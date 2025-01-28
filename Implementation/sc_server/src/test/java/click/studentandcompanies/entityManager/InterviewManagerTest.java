package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.InternshipPosOfferStatusEnum;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityRepository.*;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Condensed style tests for InterviewManager.
 * Each method covers both success and error scenarios.
 */
class InterviewManagerTest extends EntityFactory {

    @Mock
    private InterviewRepository interviewRepository;
    @Mock
    private UserManager userManager;
    @Mock
    private InterviewTemplateRepository interviewTemplateRepository;
    @Mock
    private InternshipPosOfferRepository internshipPosOfferRepository;

    @InjectMocks
    private InterviewManager interviewManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendInterviewAnswer() {
        // Success scenario
        Interview interview = setNewInterview(null, null, null, null);
        interview.setId(100);
        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", "10");
        payload.put("answer", Map.of("q1", "answer1"));

        Student student = setNewStudent(10, "Alice", setNewUniversity(1, "Uni", "IT"));
        when(interviewRepository.getInterviewById(100)).thenReturn(interview);
        when(userManager.getStudentById("10")).thenReturn(student);
        when(interviewRepository.save(interview)).thenReturn(interview);

        Interview result = interviewManager.sendInterviewAnswer(100, payload);
        assertNotNull(result);
        assertEquals(100, result.getId());
        assertTrue(result.getAnswer().contains("answer1"));

        // Interview not found
        when(interviewRepository.getInterviewById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterviewAnswer(999, payload)
        );

        // Missing student_id
        Map<String, Object> noStudentPayload = new HashMap<>(payload);
        noStudentPayload.remove("student_id");
        assertThrows(BadInputException.class, () ->
                interviewManager.sendInterviewAnswer(100, noStudentPayload)
        );

        // Student not found
        when(userManager.getStudentById("10")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterviewAnswer(100, payload)
        );

        // Missing answer
        when(userManager.getStudentById("10")).thenReturn(student); // restore valid student
        Map<String, Object> noAnswerPayload = new HashMap<>();
        noAnswerPayload.put("student_id", "10");
        assertThrows(BadInputException.class, () ->
                interviewManager.sendInterviewAnswer(100, noAnswerPayload)
        );
    }

    @Test
    void testSendInterview() {
        Interview interview = setNewInterview(null, null, null, null);
        interview.setId(200);
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);

        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", "20");
        payload.put("questions", Map.of("q1", "First question"));

        Company company = setNewCompany(20, "Google", "US");
        when(interviewRepository.getInterviewById(200)).thenReturn(interview);
        when(userManager.getCompanyById("20")).thenReturn(company);

        InterviewTemplate template = setNewInterviewTemplate(company);
        when(interviewTemplateRepository.save(any(InterviewTemplate.class))).thenReturn(template);
        when(interviewRepository.save(interview)).thenReturn(interview);

        // Success
        Interview result = interviewManager.sendInterview(200, payload);
        assertNotNull(result);
        assertEquals(InterviewStatusEnum.submitted, result.getStatus());
        assertNotNull(result.getInterviewTemplate());

        // Interview not found
        when(interviewRepository.getInterviewById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterview(999, payload)
        );

        // Already submitted
        interview.setStatus(InterviewStatusEnum.submitted);
        assertThrows(BadInputException.class, () ->
                interviewManager.sendInterview(200, payload)
        );

        // Missing company_id
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);
        Map<String, Object> noCompanyPayload = new HashMap<>();
        noCompanyPayload.put("questions", Map.of("q1", "First question"));
        assertThrows(BadInputException.class, () ->
                interviewManager.sendInterview(200, noCompanyPayload)
        );

        // Company not found
        when(userManager.getCompanyById("20")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterview(200, payload)
        );
    }

    @Test
    void testSaveInterviewTemplate() {
        Interview interview = setNewInterview(null, null, null, null);
        interview.setId(300);

        Map<String, Object> payload = Map.of("company_id", "20", "questions", Map.of("q1", "text"));
        Company company = setNewCompany(20, "Google", "US");

        when(interviewRepository.getInterviewById(300)).thenReturn(interview);
        when(userManager.getCompanyById("20")).thenReturn(company);

        InterviewTemplate template = setNewInterviewTemplate(company);
        when(interviewTemplateRepository.save(any(InterviewTemplate.class))).thenReturn(template);

        // Success
        InterviewTemplate result = interviewManager.saveInterviewTemplate(300, payload);
        assertNotNull(result);

        // Interview not found
        when(interviewRepository.getInterviewById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.saveInterviewTemplate(999, payload)
        );

        // Missing company_id
        Map<String, Object> noCompanyPayload = new HashMap<>(payload);
        noCompanyPayload.remove("company_id");
        assertThrows(BadInputException.class, () ->
                interviewManager.saveInterviewTemplate(300, noCompanyPayload)
        );

        // Company not found
        when(userManager.getCompanyById("20")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.saveInterviewTemplate(300, payload)
        );
    }

    @Test
    void testSendInterviewTemplate() {
        Company company = setNewCompany(20, "Google", "US");
        Interview interview = setNewInterview(null, setNewRecommendation(setNewInternshipOffer(company), setNewCv(setNewStudent("Marco", setNewUniversity("Uni", "IT")))),null, null);
        interview.setId(400);
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);

        InterviewTemplate template = setNewInterviewTemplate(company);
        template.setId(500);
//        interview.setInterviewTemplate(template);

        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", "20");

        System.out.println("payload: " + payload.get("company_id") + " of Type: " + payload.get("company_id").getClass() + "\ncompany: " + company.getId() + " of Type: " + company.getId().getClass());

        when(interviewRepository.getInterviewById(400)).thenReturn(interview);
        when(interviewTemplateRepository.getInterviewTemplateById(500)).thenReturn(template);
        when(interviewRepository.save(interview)).thenReturn(interview);

        // Success
        Interview result = interviewManager.sendInterviewTemplate(400, 500, payload);
        assertNotNull(result);
        assertEquals(InterviewStatusEnum.submitted, result.getStatus());

        // Interview not found
        when(interviewRepository.getInterviewById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterviewTemplate(999, 500, payload)
        );

        // Template not found
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);
        interview.setInterviewTemplate(null);
        when(interviewTemplateRepository.getInterviewTemplateById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterviewTemplate(400, 999, payload)
        );

        // Company mismatch => Unauthorized
        payload.put("company_id", "21");
        assertThrows(UnauthorizedException.class, () ->
                interviewManager.sendInterviewTemplate(400, 500, payload)
        );

        // Already has template or status != toBeSubmitted => BadInput
        payload.put("company_id", "20");
        interview.setInterviewTemplate(template);
        assertThrows(BadInputException.class, () ->
                interviewManager.sendInterviewTemplate(400, 500, payload)
        );
    }

    @Test
    void testEvaluateInterview() {
        Interview interview = setNewInterview(null, null, null, null);
        interview.setId(600);
        interview.setStatus(InterviewStatusEnum.submitted);

        when(interviewRepository.getInterviewById(600)).thenReturn(interview);
        when(interviewRepository.save(interview)).thenReturn(interview);

        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", "20");
        payload.put("evaluation", 4);
        payload.put("status", "passed");

        // Success
        Interview result = interviewManager.evaluateInterview(600, payload);
        assertNotNull(result);
        assertEquals(InterviewStatusEnum.passed, result.getStatus());
        assertEquals(4, result.getEvaluation());

        // Interview not found
        when(interviewRepository.getInterviewById(999)).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.evaluateInterview(999, payload)
        );

        // Wrong status
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);
        assertThrows(BadInputException.class, () ->
                interviewManager.evaluateInterview(600, payload)
        );

        // Missing fields => BadInput
        interview.setStatus(InterviewStatusEnum.submitted);
        Map<String, Object> noStatus = new HashMap<>(payload);
        noStatus.remove("status");
        assertThrows(BadInputException.class, () ->
                interviewManager.evaluateInterview(600, noStatus)
        );

        // Status not in [passed|failed] => BadInput
        Map<String, Object> wrongStatus = new HashMap<>(payload);
        wrongStatus.put("status", "random");
        assertThrows(BadInputException.class, () ->
                interviewManager.evaluateInterview(600, wrongStatus)
        );

        // evaluation out of range
        Map<String, Object> outOfRangeEval = new HashMap<>(payload);
        outOfRangeEval.put("evaluation", 10);
        assertThrows(BadInputException.class, () ->
                interviewManager.evaluateInterview(600, outOfRangeEval)
        );
    }

    @Test
    void testSendInterviewPositionOffer() {
        Interview interview = setNewInterview(null, null, null, null);
        interview.setId(700);
        interview.setStatus(InterviewStatusEnum.passed);

        when(interviewRepository.findById(700)).thenReturn(Optional.of(interview));

        Map<String, Object> payload = new HashMap<>();
        payload.put("company_id", "20");
        //payload.put("student_id", "10");

        Company company = setNewCompany(20, "Google", "US");
        InternshipOffer offer = setNewInternshipOffer(company);
        Recommendation rec = setNewRecommendation(offer, setNewCv(setNewStudent(10, "Alice", setNewUniversity(1,"Uni","IT"))));
        interview.setRecommendation(rec);

        when(userManager.getCompanyById("20")).thenReturn(company);

        // Success
        InternshipPosOffer posOffer = new InternshipPosOffer();
        posOffer.setId(900);
        when(internshipPosOfferRepository.save(any(InternshipPosOffer.class))).thenReturn(posOffer);
        Interview savedInterview = setNewInterview(null, null, null, posOffer);
        when(interviewRepository.save(interview)).thenReturn(savedInterview);

        InternshipPosOffer result = interviewManager.sendInterviewPositionOffer(700, payload);
        assertNotNull(result);

        // Interview not found
        when(interviewRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterviewPositionOffer(999, payload)
        );

        // Already has internshipPosOffer => WrongStateException
        interview.setInternshipPosOffer(posOffer);
        assertThrows(WrongStateException.class, () ->
                interviewManager.sendInterviewPositionOffer(700, payload)
        );

        // Missing company_id => BadInput
        interview.setInternshipPosOffer(null);
        Map<String, Object> noCompanyPayload = new HashMap<>();
        assertThrows(BadInputException.class, () ->
                interviewManager.sendInterviewPositionOffer(700, noCompanyPayload)
        );

        // Company not found
        when(userManager.getCompanyById("20")).thenReturn(null);
        assertThrows(NotFoundException.class, () ->
                interviewManager.sendInterviewPositionOffer(700, payload)
        );

        // Mismatch company
        when(userManager.getCompanyById("20")).thenReturn(setNewCompany(21, "Another", "DE"));
        assertThrows(BadInputException.class, () ->
                interviewManager.sendInterviewPositionOffer(700, payload)
        );

        // Interview status != passed => WrongStateException
        when(userManager.getCompanyById("20")).thenReturn(company);
        interview.setStatus(InterviewStatusEnum.failed);
        assertThrows(WrongStateException.class, () ->
                interviewManager.sendInterviewPositionOffer(700, payload)
        );
    }

    @Test
    void testAcceptInternshipPositionOffer() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("student_id", "10");

        Interview interview = setNewInterview(null, null, null, null);
        InternshipPosOffer posOffer = setNewInternshipPosOffer();
        interview.setInternshipPosOffer(posOffer);

        Student student = setNewStudent(10, "Alice", setNewUniversity(1, "Uni", "IT"));
        Recommendation rec = setNewRecommendation(
                setNewInternshipOffer(setNewCompany(20,"Google","US")),
                setNewCv(student)
        );
        interview.setRecommendation(rec);
        interview.setSpontaneousApplication(null);
        when(interviewRepository.getInterviewByInternshipPosOffer_Id(900)).thenReturn(interview);

        // 1) Success
        when(userManager.getUserType("10")).thenReturn(UserType.STUDENT);
        InternshipPosOffer result = interviewManager.acceptInternshipPositionOffer(900, payload);
        assertSame(InternshipPosOfferStatusEnum.accepted, result.getStatus());

        // 2) UserType UNKNOWN => BadInput
        when(userManager.getUserType("10")).thenReturn(UserType.UNKNOWN);
        assertThrows(BadInputException.class, () ->
                interviewManager.acceptInternshipPositionOffer(900, payload)
        );

        // 3) If no interview => NotFoundException
        when(interviewRepository.getInterviewByInternshipPosOffer_Id(999)).thenReturn(null);
        when(userManager.getUserType("10")).thenReturn(UserType.STUDENT);
        assertThrows(NotFoundException.class, () ->
                interviewManager.acceptInternshipPositionOffer(999, payload)
        );

        // 4) If acceptance already true => WrongState
        posOffer.setStatus(InternshipPosOfferStatusEnum.accepted);
        assertThrows(WrongStateException.class, () ->
                interviewManager.acceptInternshipPositionOffer(900, payload)
        );

        // 5) Mismatch student => Unauthorized
        posOffer.setStatus(InternshipPosOfferStatusEnum.pending);
        Student other = setNewStudent(99, "OtherStudent", setNewUniversity(2,"OtherUni","FR"));
        rec.getCv().setStudent(other);
        assertThrows(UnauthorizedException.class, () ->
                interviewManager.acceptInternshipPositionOffer(900, payload)
        );
    }
}
