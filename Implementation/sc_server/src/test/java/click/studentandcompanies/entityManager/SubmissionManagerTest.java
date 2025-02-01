package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.SpontaneousApplicationStatusEnum;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.entityRepository.*;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Condensed style tests for SubmissionManager: one test method per public method,
 * each covering multiple scenarios (success, errors, etc.).
 */
class SubmissionManagerTest extends EntityFactory {

    @Mock
    private CvRepository cvRepository;
    @Mock
    private InternshipOfferRepository internshipOfferRepository;
    @Mock
    private SpontaneousApplicationRepository spontaneousApplicationRepository;
    @Mock
    private UserManager userManager;

    @InjectMocks
    private SubmissionManager submissionManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetInternshipsByCompany() throws NotFoundException, NoContentException {
        // Success
        Company company = setNewCompany(20, "Google", "US");
        when(userManager.getCompanyById("20")).thenReturn(company);

        InternshipOffer offer1 = setNewInternshipOffer(company);
        InternshipOffer offer2 = setNewInternshipOffer(company);
        when(internshipOfferRepository.getInternshipOfferByCompanyId("20"))
                .thenReturn(List.of(offer1, offer2));

        List<InternshipOffer> result = submissionManager.getInternshipsByCompany("20");
        assertNotNull(result);
        assertEquals(2, result.size());

        // Company not found
        when(userManager.getCompanyById("99")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                submissionManager.getInternshipsByCompany("99")
        );

        // No content
        when(userManager.getCompanyById("30")).thenReturn(setNewCompany(30, "NoOffersCo", "IT"));
        when(internshipOfferRepository.getInternshipOfferByCompanyId("30"))
                .thenReturn(Collections.emptyList());
        assertThrows(NoContentException.class, () ->
                submissionManager.getInternshipsByCompany("30")
        );
    }

    @Test
    void testGetCvByStudent() throws NotFoundException, NoContentException {
        // Success
        University uni = setNewUniversity(1, "UniA", "IT");
        Student student = setNewStudent(10, "Alice", uni);
        Cv cv = setNewCv(student);
        when(userManager.getStudentById("10")).thenReturn(student);
        when(cvRepository.getCvByStudent_Id("10")).thenReturn(cv);

        Cv result = submissionManager.getCvByStudent("10");
        assertNotNull(result);
        assertEquals(cv, result);

        // Student not found
        when(userManager.getStudentById("99")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                submissionManager.getCvByStudent("99")
        );

        // No CV
        when(userManager.getStudentById("11")).thenReturn(setNewStudent(11, "Bob", uni));
        when(cvRepository.getCvByStudent_Id("11")).thenReturn(null);
        assertThrows(NoContentException.class, () ->
                submissionManager.getCvByStudent("11")
        );
    }

    @Test
    void testUpdateCvCall() throws BadInputException, NotFoundException {
        University uni = setNewUniversity(1, "UniA", "IT");
        Student student = setNewStudent(10, "Alice", uni);

        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", "10");
        payload.put("update_time", Instant.now().toString());
        payload.put("skills", "New skills");

        when(userManager.getStudentById("10")).thenReturn(student);

        // Updating existing CV
        Cv existingCv = setNewCv(student);
        when(cvRepository.getCvByStudent_Id("10")).thenReturn(existingCv);
        when(cvRepository.save(existingCv)).thenReturn(existingCv);

        Cv result = submissionManager.updateCvCall(payload);
        assertNotNull(result);
        assertTrue(result.getSkills().contains("New skills"));

        // Creating a new CV (none found)
        when(cvRepository.getCvByStudent_Id("10")).thenReturn(null);
        Cv newCv = setNewCv(student);
        when(cvRepository.save(any(Cv.class))).thenReturn(newCv);
        result = submissionManager.updateCvCall(payload);
        assertNotNull(result);

        // Missing student_id
        Map<String, Object> badPayload = new HashMap<>(payload);
        badPayload.remove("student_id");
        assertThrows(BadInputException.class, () ->
                submissionManager.updateCvCall(badPayload)
        );

        // Student not found
        when(userManager.getStudentById("10")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                submissionManager.updateCvCall(payload)
        );

        // Missing update_time
        when(userManager.getStudentById("10")).thenReturn(student);
        Map<String, Object> missingTime = new HashMap<>();
        missingTime.put("student_id", "10");
        assertThrows(BadInputException.class, () ->
                submissionManager.updateCvCall(missingTime)
        );
    }

    @Test
    void testUpdateInternshipOffer() throws UnauthorizedException, BadInputException, NotFoundException {
        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", "20");

        // Creating a new offer (no internshipOffer_id)
        Company company = setNewCompany(20, "Google", "US");
        when(userManager.getCompanyById("20")).thenReturn(company);
        payload.put("title", "Sample Offer");
        payload.put("description", "Desc");
        payload.put("compensation", 1000);
        payload.put("location", "Remote");
        payload.put("duration_hours", 40);
        payload.put("start_date", LocalDate.now().toString());
        payload.put("end_date", LocalDate.now().plusDays(1).toString());
        InternshipOffer newOffer = setNewInternshipOffer(company);
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(newOffer);

        InternshipOffer created = submissionManager.updateInternshipOffer(payload);
        assertNotNull(created);

        // Updating existing offer
        payload.put("internshipOffer_id", 999);
        InternshipOffer existingOffer = setNewInternshipOffer(999, company);
        when(internshipOfferRepository.getInternshipOfferById(999)).thenReturn(existingOffer);
        when(internshipOfferRepository.getInternshipOfferByCompanyId("20")).thenReturn(List.of(existingOffer));
        InternshipOffer updated = setNewInternshipOffer(999, company);
        when(internshipOfferRepository.save(any(InternshipOffer.class))).thenReturn(updated);
        InternshipOffer result = submissionManager.updateInternshipOffer(payload);
        assertNotNull(result);

        // Missing company_id
        Map<String, Object> noCompany = new HashMap<>();
        assertThrows(BadInputException.class, () ->
                submissionManager.updateInternshipOffer(noCompany)
        );

        // Company not found
        when(userManager.getCompanyById("20")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                submissionManager.updateInternshipOffer(payload)
        );

        // Offer not found
        when(userManager.getCompanyById("20")).thenReturn(company);
        when(internshipOfferRepository.getInternshipOfferById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                submissionManager.updateInternshipOffer(payload)
        );

        // Unauthorized company
        InternshipOffer anOffer = setNewInternshipOffer(999, setNewCompany(21, "OtherCo", "DE"));
        when(internshipOfferRepository.getInternshipOfferById(999)).thenReturn(anOffer);
        assertThrows(UnauthorizedException.class, () ->
                submissionManager.updateInternshipOffer(payload)
        );

        // Start date > end date
        payload.remove("internshipOffer_id");
        payload.put("end_date", LocalDate.now().minusDays(1).toString());
        assertThrows(BadInputException.class, () ->
                submissionManager.updateInternshipOffer(payload)
        );
    }

    @Test
    void testGetSpontaneousApplicationsByParticipant() throws NotFoundException, NoContentException, BadInputException {
        // Student scenario
        when(userManager.getUserType("10")).thenReturn(UserType.STUDENT);
        SpontaneousApplication app1 = new SpontaneousApplication();
        SpontaneousApplication app2 = new SpontaneousApplication();
        when(spontaneousApplicationRepository.findSpontaneousApplicationByStudentId("10"))
                .thenReturn(List.of(app1, app2));
        List<SpontaneousApplication> result = submissionManager.getSpontaneousApplicationsByParticipant("10");
        assertEquals(2, result.size());

        // Company scenario
        when(userManager.getUserType("20")).thenReturn(UserType.COMPANY);
        SpontaneousApplication appC = new SpontaneousApplication();
        when(spontaneousApplicationRepository.findSpontaneousApplicationByCompanyId("20"))
                .thenReturn(List.of(appC));
        result = submissionManager.getSpontaneousApplicationsByParticipant("20");
        assertEquals(1, result.size());

        // University => BadInput
        when(userManager.getUserType("30")).thenReturn(UserType.UNIVERSITY);
        assertThrows(BadInputException.class, () ->
                submissionManager.getSpontaneousApplicationsByParticipant("30")
        );

        // Unknown user => NotFound
        when(userManager.getUserType("99")).thenReturn(UserType.UNKNOWN);
        assertThrows(NotFoundException.class, () ->
                submissionManager.getSpontaneousApplicationsByParticipant("99")
        );

        // No content
        when(userManager.getUserType("40")).thenReturn(UserType.STUDENT);
        when(spontaneousApplicationRepository.findSpontaneousApplicationByStudentId("40"))
                .thenReturn(Collections.emptyList());
        assertThrows(NoContentException.class, () ->
                submissionManager.getSpontaneousApplicationsByParticipant("40")
        );
    }

    @Test
    void testSubmitSpontaneousApplication() throws BadInputException, NotFoundException {
        // Success
        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", "10");

        Student student = setNewStudent(10, "Alice", setNewUniversity(1, "Uni", "IT"));
        when(userManager.getStudentById("10")).thenReturn(student);

        InternshipOffer offer = setNewInternshipOffer(999, setNewCompany(20, "Google", "US"));
        when(internshipOfferRepository.getInternshipOfferById(999)).thenReturn(offer);

        SpontaneousApplication savedApp = new SpontaneousApplication(student, offer, SpontaneousApplicationStatusEnum.toBeEvaluated);
        when(spontaneousApplicationRepository.save(any(SpontaneousApplication.class))).thenReturn(savedApp);

        SpontaneousApplication result = submissionManager.submitSpontaneousApplication(999, "10");
        assertNotNull(result);

        // Internship not found
        when(internshipOfferRepository.getInternshipOfferById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                submissionManager.submitSpontaneousApplication(999, "10")
        );

        // Invalid student_id
//        assertThrows(NotFoundException.class, () ->
//                submissionManager.submitSpontaneousApplication(999, "99")
//        );

        // Student not found
        when(internshipOfferRepository.getInternshipOfferById(999)).thenReturn(offer);
        when(userManager.getStudentById("10")).thenReturn(null);
        assertThrows(BadInputException.class, () ->
                submissionManager.submitSpontaneousApplication(999, "10")
        );
    }

    @Test
    void testCloseInternshipOffer() throws NotFoundException, UnauthorizedException {
        InternshipOffer offer = setNewInternshipOffer(100, setNewCompany(20, "Google", "US"));
        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", "20");

        when(internshipOfferRepository.getInternshipOfferById(100)).thenReturn(offer);

        // Success
        doNothing().when(internshipOfferRepository).removeInternshipOfferById(100);
        InternshipOffer result = submissionManager.closeInternshipOffer(100, payload);
        assertNotNull(result);
        assertEquals(offer, result);

        // Offer not found
        when(internshipOfferRepository.getInternshipOfferById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                submissionManager.closeInternshipOffer(999, payload)
        );

        // Unauthorized
        offer.setCompany(setNewCompany(21,"OtherCo","DE"));
        when(internshipOfferRepository.getInternshipOfferById(100)).thenReturn(offer);
        assertThrows(UnauthorizedException.class, () ->
                submissionManager.closeInternshipOffer(100, payload)
        );
    }

    @Test
    void testCloseRelatedApplications() {
        // This method returns void and simply calls removeSpontaneousApplicationByInternshipOffer_Id
        doNothing().when(spontaneousApplicationRepository).removeSpontaneousApplicationByInternshipOffer_Id(888);

        submissionManager.closeRelatedApplications(888);
        verify(spontaneousApplicationRepository).removeSpontaneousApplicationByInternshipOffer_Id(888);
    }
}
