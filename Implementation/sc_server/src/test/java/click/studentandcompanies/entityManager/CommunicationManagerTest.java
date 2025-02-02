package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.CommunicationTypeEnum;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.entityRepository.MessageRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for CommunicationManager.
 * <p>
 * This class extends EntityFactory so that dummy entities (Student, University, Company, etc.)
 * can be created via the factory methods. One test method is provided per public method
 * in CommunicationManager.
 */
@ExtendWith(MockitoExtension.class)
public class CommunicationManagerTest extends EntityFactory {

    @Mock
    private UserManager userManager;
    @Mock
    private CommunicationRepository communicationRepository;
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private CommunicationManager communicationManager;

    /**
     * Helper method to build a dummy Communication that has a full nested chain
     * so that getAllUserCommunications and getCommunicationMessages can work.
     * This method creates a Student (with id equal to the passed userId),
     * a dummy University, a dummy Company, and uses a mocked InternshipPosOffer
     * whose Interview returns a Recommendation (with a CV pointing to the Student).
     *
     * @param userId the id of the student user to “own” the communication
     * @return a fully configured Communication instance
     */
    private Communication buildCommunicationForStudent(Integer userId) {
        // Create a real student and university using the factory
        University uni = setNewUniversity(1, "Test University", "Country");
        Student student = setNewStudent(userId, "Test Student", uni);
        Company company = setNewCompany(1, "Test Company", "Country");

        // Build the nested chain using mocks:
        // internshipPosOff -> interview -> recommendation -> cv -> student
        InternshipPosOffer ipo = mock(InternshipPosOffer.class);
        Interview interview = mock(Interview.class);
        Recommendation recommendation = mock(Recommendation.class);
        Cv cv = mock(Cv.class);

        // Stub the chain so that recommendation.getCv().getStudent().getId() equals userId.
        when(cv.getStudent()).thenReturn(student);
        when(recommendation.getCv()).thenReturn(cv);
        // In this test we use the recommendation branch (spontaneousApplication is null)
        when(interview.getRecommendation()).thenReturn(recommendation);
        when(ipo.getInterview()).thenReturn(interview);

        // Create a Communication using the real constructor.
        // (ParticipantTypeEnum and CommunicationTypeEnum values must match your enums.)
        return new Communication(student, company, ParticipantTypeEnum.student, ipo,
                "Test Title", "Test Content", CommunicationTypeEnum.communication);
    }

    /**
     * Tests getAllUserCommunications(String userID).
     * <p>
     * This test simulates a student user whose id appears in the nested recommendation chain.
     */
    @Test
    public void testGetAllUserCommunications() throws NotFoundException, NoContentException {
        Integer userId = 10;
        when(userManager.getUserType(userId.toString())).thenReturn(UserType.STUDENT);

        Communication comm = buildCommunicationForStudent(userId);
        List<Communication> communications = Collections.singletonList(comm);
        when(communicationRepository.findAll()).thenReturn(communications);

        List<Communication> result = communicationManager.getAllUserCommunications(userId.toString());
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    /**
     * Tests getCommunicationMessages(Integer commID, String userID).
     * <p>
     * This test creates a dummy communication (with proper nested objects) so that the student user
     * is authorized, stubs a nonempty messages list, and then verifies that the returned message
     * list contains the expected message.
     */
    @Test
    public void testGetCommunicationMessages() throws NotFoundException, UnauthorizedException, NoContentException {
        Integer userId = 10;
        int commId = 1;
        Communication comm = buildCommunicationForStudent(userId);
        when(communicationRepository.findById(commId)).thenReturn(Optional.of(comm));

        Message message = new Message();
        message.setBody("Hello Message");
        List<Message> messages = Collections.singletonList(message);
        when(messageRepository.getMessagesByCommunication_IdOrderByTimeStamp(commId)).thenReturn(messages);

        List<Message> result = communicationManager.getCommunicationMessages(commId, userId.toString());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hello Message", result.getFirst().getBody());
    }

    /**
     * Tests createCommunication(Map<String, Object> payload).
     * <p>
     * This test sets up a payload for a student user (using factory methods to create a student,
     * university, and internship offer), stubs the save call, and then verifies that the returned
     * Communication has the expected title and communication type.
     */
    @Test
    public void testCreateCommunication() {
        Integer userId = 10;
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", userId.toString());
        payload.put("internshipPosOfferID", 10);
        payload.put("title", "Comm Title");
        payload.put("content", "Comm Content");
        payload.put("communicationType", "communication");

        // Stub responses from userManager for a STUDENT.
        when(userManager.getUserType(Integer.toString(userId))).thenReturn(UserType.STUDENT);
        // Create a student using the factory.
        University uni = setNewUniversity(1, "Test University", "Country");
        Student student = setNewStudent(userId, "Test Student", uni);
        when(userManager.getStudentById(Integer.toString(userId))).thenReturn(student);
        // Create a dummy internship offer.
        InternshipPosOffer ipo = setNewInternshipPosOffer();
        when(userManager.getInternshipPosOfferById(10)).thenReturn(ipo);

        Communication savedComm = new Communication(student, null, ParticipantTypeEnum.student,
                ipo, "Comm Title", "Comm Content", CommunicationTypeEnum.communication);
        when(communicationRepository.save(any(Communication.class))).thenReturn(savedComm);

        Communication result = communicationManager.createCommunication(payload);
        assertNotNull(result);
        assertEquals("Comm Title", result.getTitle());
        assertEquals(CommunicationTypeEnum.communication, result.getCommunicationType());
    }

    /**
     * Tests createMessage(String userID, Integer commID, Map<String, Object> payload).
     * <p>
     * This test creates a dummy communication (with a proper nested chain so that the student is
     * authorized), stubs the repository’s save, and then verifies that the returned Message has
     * the expected body and sender name.
     */
    @Test
    public void testCreateMessage() throws NotFoundException, UnauthorizedException, BadInputException {
        Integer userId = 10;
        int commId = 1;
        Map<String, Object> payload = new HashMap<>();
        payload.put("body", "Message Body");

        when(userManager.getUserType(userId.toString())).thenReturn(UserType.STUDENT);
        Communication comm = buildCommunicationForStudent(userId);
        when(communicationRepository.findById(commId)).thenReturn(Optional.of(comm));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Message result = communicationManager.createMessage(userId.toString(), commId, payload);
        assertNotNull(result);
        assertEquals("Message Body", result.getBody());
        // According to the command logic, the sender name is taken from the student name in the recommendation.
        assertEquals("Test Student", result.getSenderName());
    }

    /**
     * Tests terminateCommunication(int communicationID, Map<String, Object> payload).
     * <p>
     * This test creates a real Communication (using factory methods) whose student’s university matches
     * the one specified in the payload. It then stubs the repository’s find and save calls and verifies
     * that the Communication’s type is changed to closed.
     */
    @Test
    public void testTerminateCommunication() {
        int commId = 1;
        Map<String, Object> payload = new HashMap<>();
        // Create a University using the factory (using a fixed id so that reference equality holds).
        University uni = setNewUniversity(1, "Test University", "Country");
        payload.put("university_id", uni.getId());

        // Create a student (using the same University) and a company.
        Student student = setNewStudent(10, "Test Student", uni);
        Company company = setNewCompany(1, "Test Company", "Country");
        Communication comm = setNewCommunication(student, company);
        comm.setCommunicationType(CommunicationTypeEnum.communication);

        when(communicationRepository.findById(commId)).thenReturn(Optional.of(comm));
        when(userManager.getUniversityById(uni.getId())).thenReturn(uni);
        when(communicationRepository.save(any(Communication.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Communication result = communicationManager.terminateCommunication(commId, payload);
        assertNotNull(result);
        assertEquals(CommunicationTypeEnum.closed, result.getCommunicationType());
    }
}
