package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.CommunicationTypeEnum;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.entityRepository.MessageRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommunicationManagerTest extends EntityFactory {

    @Mock
    private UserManager userManager;
    @Mock
    private CommunicationRepository communicationRepository;
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private CommunicationManager communicationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUserCommunications() throws NotFoundException, NoContentException {
        // Setup common entities
        University uni = setNewUniversity(1, "Uni", "IT");
        Student s1 = setNewStudent(10, "Marco", uni);
        Student s2 = setNewStudent(11, "Luca", uni);
        Company c1 = setNewCompany(20, "Google", "US");
        InternshipOffer offer = setNewInternshipOffer(c1);

        Communication comm1 = setNewCommunication(s1, offer);
        comm1.setId(101);
        Communication comm2 = setNewCommunication(s2, offer);
        comm2.setId(102);

        // Behavior for user types
        when(userManager.getUserType("10")).thenReturn(UserType.STUDENT);
        when(userManager.getUserType("11")).thenReturn(UserType.STUDENT);
        when(userManager.getUserType("20")).thenReturn(UserType.COMPANY);
        when(userManager.getUserType("1")).thenReturn(UserType.UNIVERSITY);
        when(userManager.getUserType("999")).thenReturn(UserType.UNKNOWN);

        // Behavior for communications
        when(communicationRepository.findCommunicationByStudent_Id("10")).thenReturn(List.of(comm1));
        when(communicationRepository.findCommunicationByStudent_Id("11")).thenReturn(List.of(comm2));
        when(communicationRepository.findCommunicationByCompany_Id("20")).thenReturn(List.of(comm1, comm2));
        when(communicationRepository.findCommunicationByUniversity_Id("1")).thenReturn(List.of(comm1, comm2));

        // 1) Student s1
        List<Communication> result = communicationManager.getAllUserCommunications("10");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(101, result.getFirst().getId());

        // 2) Student s2
        result = communicationManager.getAllUserCommunications("11");
        assertEquals(1, result.size());
        assertEquals(102, result.getFirst().getId());

        // 3) Company c1
        result = communicationManager.getAllUserCommunications("20");
        assertEquals(2, result.size());

        // 4) University
        result = communicationManager.getAllUserCommunications("1");
        assertEquals(2, result.size());

        // 5) Unknown => NotFoundException
        assertThrows(NotFoundException.class, () -> communicationManager.getAllUserCommunications("999"));

        // 6) NoContentException scenario
        when(communicationRepository.findCommunicationByStudent_Id("11")).thenReturn(Collections.emptyList());
        assertThrows(NoContentException.class, () -> communicationManager.getAllUserCommunications("11"));

        // Verify calls
        verify(communicationRepository).findCommunicationByStudent_Id("10");
        verify(communicationRepository, times(2)).findCommunicationByStudent_Id("11");
        verify(communicationRepository).findCommunicationByCompany_Id("20");
        verify(communicationRepository).findCommunicationByUniversity_Id("1");
    }

    @Test
    void testCreateCommunication() throws NotFoundException {
        // Valid scenario
        University uni = setNewUniversity(1, "Uni", "IT");
        Student student = setNewStudent(10, "Marco", uni);
        Company company = setNewCompany(20, "Google", "US");
        InternshipOffer offer = setNewInternshipOffer(company);

        when(userManager.getStudentById("10")).thenReturn(student);
        when(userManager.getUniversityById("1")).thenReturn(uni);
        when(userManager.getInternshipOfferById(30)).thenReturn(offer);

        Map<String, Object> validPayload = new HashMap<>();
        validPayload.put("student_id", "10");
        validPayload.put("university_id", "1");
        validPayload.put("internshipOffer_id", 30);
        validPayload.put("title", "Test");
        validPayload.put("content", "Test content");
        validPayload.put("communication_type", "communication");

        Communication created = communicationManager.createCommunication(validPayload);
        assertNotNull(created);
        assertEquals(student, created.getStudent());
        assertEquals(uni, created.getUniversity());
        assertEquals(offer, created.getInternshipOffer());
        assertEquals(CommunicationTypeEnum.communication, created.getCommunicationType());

        // Missing field => BadInputException
        Map<String, Object> missingFieldPayload = new HashMap<>(validPayload);
        missingFieldPayload.remove("title"); // removing a required field
        assertThrows(BadInputException.class, () -> communicationManager.createCommunication(missingFieldPayload));

        // Student from different university => BadInputException
        University otherUni = setNewUniversity(2, "OtherUni", "FR");
        student.setUniversity(otherUni);
        assertThrows(BadInputException.class, () -> communicationManager.createCommunication(validPayload));

        // Communication type is "closed" => WrongStateException
        student.setUniversity(uni);
        Map<String, Object> closedTypePayload = new HashMap<>(validPayload);
        closedTypePayload.put("communication_type", "closed");
        assertThrows(WrongStateException.class, () -> communicationManager.createCommunication(closedTypePayload));
    }

    @Test
    void testGetCommunicationMessages() throws NotFoundException, UnauthorizedException {
        // Communication exists
        Communication comm = setNewCommunication(
                                    setNewStudent(10, "Marco",
                                            setNewUniversity(1, "Uni", "IT")),
                                    setNewInternshipOffer(
                                            setNewCompany(20, "Google", "US")));
        comm.setId(100);

        when(communicationRepository.getCommunicationById(100)).thenReturn(comm);
        when(messageRepository.getMessagesByCommunication_Id(100)).thenReturn(List.of(
                setNewMessage(1, "Hello", "Marco", comm),
                setNewMessage(2, "Hi", "Google", comm)
        ));

        // Authorized
        when(userManager.getUserType("10")).thenReturn(UserType.STUDENT);
        List<Message> fetched = communicationManager.getCommunicationMessages(100, "10");
        assertEquals(1, fetched.getFirst().getId());

        // NotFound
        when(communicationRepository.getCommunicationById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> communicationManager.getCommunicationMessages(999, "10"));

        // Unauthorized
        when(userManager.getUserType("99")).thenReturn(UserType.STUDENT);
        assertThrows(UnauthorizedException.class, () -> communicationManager.getCommunicationMessages(100, "99"));
    }

    @Test
    void testTerminateCommunication() {
        University uni = setNewUniversity(1, "Poli", "IT");
        Student student = setNewStudent(10, "Marco", uni);
        Communication comm = setNewCommunication(student, setNewInternshipOffer(setNewCompany(20, "Google", "US")));
        comm.setId(200);

        when(userManager.getUniversityById("1")).thenReturn(uni);
        when(communicationRepository.findById(200)).thenReturn(Optional.of(comm));
        when(communicationRepository.save(comm)).thenReturn(comm);

        Map<String, Object> validPayload = Map.of("university_id", "1");

        // Success
        Communication result = communicationManager.terminateCommunication(200, validPayload);
        assertNotNull(result);
        assertEquals(200, result.getId());
        assertEquals(CommunicationTypeEnum.closed, result.getCommunicationType());
        verify(communicationRepository).save(comm);

        // Missing university_id => BadInputException
        Map<String, Object> missingUniPayload = Map.of();
        assertThrows(BadInputException.class, () -> communicationManager.terminateCommunication(200, missingUniPayload));

        // Communication not found
        when(communicationRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(BadInputException.class, () -> communicationManager.terminateCommunication(999, validPayload));

        // Unauthorized
        University otherUni = setNewUniversity(2, "WrongUni", "US");
        when(userManager.getUniversityById("2")).thenReturn(otherUni);
        Map<String, Object> unauthorizedPayload = Map.of("university_id", "2");
        assertThrows(UnauthorizedException.class, () -> communicationManager.terminateCommunication(200, unauthorizedPayload));
    }
}
