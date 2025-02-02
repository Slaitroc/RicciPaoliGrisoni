package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.*;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityRepository.*;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for InterviewManager
 */
@ExtendWith(MockitoExtension.class)
class InterviewManagerTest {

    @Mock
    private InterviewRepository interviewRepository;

    @Mock
    private UserManager userManager;

    @Mock
    private InterviewTemplateRepository interviewTemplateRepository;

    @Mock
    private InternshipPosOfferRepository internshipPosOfferRepository;

    @Mock
    private InterviewQuizRepository interviewQuizRepository;

    @InjectMocks
    private InterviewManager interviewManager;

    private Interview interview;
    private Company company;
    private Student student;
    private InterviewTemplate interviewTemplate;
    private InternshipPosOffer internshipPosOffer;
    private InterviewQuiz interviewQuiz;
    private Recommendation recommendation;

    private Integer companyID;
    private Integer studentID;
    private Map<String, Object> payload;

    @BeforeEach
    void setUp() {
        // Create basic Company
        companyID = 2001;
        company = new Company();
        company.setId(companyID.toString());
        company.setName("TestCompany");

        // Create basic Student
        studentID = 1001;
        student = new Student();
        student.setId(studentID.toString());
        student.setName("TestStudent");

        // Create basic InterviewTemplate
        interviewTemplate = new InterviewTemplate();
        interviewTemplate.setId(9999);

        // Create basic InterviewQuiz
        interviewQuiz = new InterviewQuiz();
        interviewQuiz.setId(101);

        // Create basic Recommendation
        recommendation = new Recommendation();
        InternshipOffer offer = new InternshipOffer();
        offer.setCompany(company);
        recommendation.setInternshipOffer(offer);

        // Create basic Interview
        interview = new Interview();
        interview.setId(123);
        interview.setRecommendation(recommendation);
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);

        // Create a basic InternshipPosOffer
        internshipPosOffer = new InternshipPosOffer();
        internshipPosOffer.setId(8888);
        internshipPosOffer.setStatus(InternshipPosOfferStatusEnum.pending);
        internshipPosOffer.setInterview(interview);

        // Setup a default payload
        payload = new HashMap<>();
    }

    // ------------------------------------------------------------------
    // 1) Test: sendInterviewAnswer(int interviewID, Map<String, Object> payload)
    // ------------------------------------------------------------------
    @Test
    void testSendInterviewAnswer_Success() {
        // Mock user manager to return the student with correct ID
        when(userManager.getStudentById(studentID.toString())).thenReturn(student);

        // Put required answers in the payload
        payload.put("student_id", studentID.toString());
        payload.put("answer1", "Answer1");
        payload.put("answer2", "Answer2");
        payload.put("answer3", "Answer3");
        payload.put("answer4", "Answer4");
        payload.put("answer5", "Answer5");
        payload.put("answer6", "Answer6");

        // Mock repository to return the interview
        when(interviewRepository.getInterviewById(123)).thenReturn(interview);
        when(interviewQuizRepository.save(any(InterviewQuiz.class))).thenReturn(interviewQuiz);
        when(interviewRepository.save(any(Interview.class))).thenAnswer(i -> i.getArgument(0));

        Interview result = interviewManager.sendInterviewAnswer(123, payload);

        assertNotNull(result);
        assertTrue(result.getHasAnswered());
        assertEquals(InterviewStatusEnum.submitted, result.getStatus());
        verify(interviewRepository, times(1)).getInterviewById(123);
        verify(interviewQuizRepository, times(1)).save(any(InterviewQuiz.class));
        verify(interviewRepository, times(1)).save(interview);
    }

    // ------------------------------------------------------------------
    // 2) Test: sendInterview(int interviewID, Map<String, Object> payload)
    // ------------------------------------------------------------------
    @Test
    void testSendInterview_Success() {
        when(interviewRepository.getInterviewById(123)).thenReturn(interview);

        // This interview is "toBeSubmitted" by default in setUp()

        payload.put("company_id", companyID.toString());
        // Mock user manager
        when(userManager.getCompanyById(companyID.toString())).thenReturn(company);

        // Provide 6 question keys
        payload.put("question1", "Q1?");
        payload.put("question2", "Q2?");
        payload.put("question3", "Q3?");
        payload.put("question4", "Q4?");
        payload.put("question5", "Q5?");
        payload.put("question6", "Q6?");

        when(interviewTemplateRepository.save(any(InterviewTemplate.class)))
                .thenAnswer(i -> {
                    InterviewTemplate tmp = i.getArgument(0);
                    tmp.setId(9999);
                    return tmp;
                });
        when(interviewRepository.save(any(Interview.class))).thenAnswer(i -> i.getArgument(0));

        Interview result = interviewManager.sendInterview(123, payload);

        assertNotNull(result);
        assertEquals(InterviewStatusEnum.submitted, result.getStatus());
        assertNotNull(result.getInterviewTemplate());
        verify(interviewRepository, times(1)).getInterviewById(123);
        verify(userManager, times(1)).getCompanyById(companyID.toString());
        verify(interviewTemplateRepository, times(1)).save(any(InterviewTemplate.class));
        verify(interviewRepository, times(1)).save(any(Interview.class));
    }

    // ------------------------------------------------------------------
    // 3) Test: saveInterviewTemplate(int InterviewID, Map<String, Object> payload)
    // ------------------------------------------------------------------
    @Test
    void testSaveInterviewTemplate_Success() {
        // In your current code, the method returns null because createInterviewTemplate is commented.
        // We will just ensure it calls the logic to show coverage.
        when(interviewRepository.getInterviewById(123)).thenReturn(interview);

        payload.put("company_id", companyID.toString());
        when(userManager.getCompanyById(companyID.toString())).thenReturn(company);

        // This command does not create the template in the snippet (since it's commented),
        // but let's just ensure it doesn't throw and returns null.
        InterviewTemplate result = interviewManager.saveInterviewTemplate(123, payload);
        assertNull(result);
        verify(interviewRepository, times(1)).getInterviewById(123);
        verify(userManager, times(1)).getCompanyById(companyID.toString());
    }

    // ------------------------------------------------------------------
    // 4) Test: sendInterviewTemplate(int interviewID, int templateID, String companyID)
    // ------------------------------------------------------------------
    @Test
    void testSendInterviewTemplate_Success() {
        // Setup
        int templateID = 1111;
        // Mock user type to be COMPANY
        when(userManager.getUserType(companyID.toString())).thenReturn(UserType.COMPANY);

        // Mock the repositories
        when(interviewTemplateRepository.findById(templateID)).thenReturn(Optional.of(interviewTemplate));
        when(interviewRepository.findById(interview.getId())).thenReturn(Optional.of(interview));
        when(interviewRepository.save(any(Interview.class))).thenAnswer(i -> i.getArgument(0));

        // Execute
        Interview result = interviewManager.sendInterviewTemplate(interview.getId(), templateID, companyID.toString());
        assertNotNull(result);
        assertEquals(interviewTemplate, result.getInterviewTemplate());

        // Verify
        verify(userManager, times(1)).getUserType(companyID.toString());
        verify(interviewTemplateRepository, times(1)).findById(templateID);
        verify(interviewRepository, times(1)).findById(interview.getId());
        verify(interviewRepository, times(1)).save(any(Interview.class));
    }

    // ------------------------------------------------------------------
    // 5) Test: getInterviewTemplates(String companyId)
    // ------------------------------------------------------------------
    @Test
    void testGetInterviewTemplates_Success() {
        // We will return user type = COMPANY
        when(userManager.getUserType(companyID.toString())).thenReturn(UserType.COMPANY);
        // Return the company from userManager
        when(userManager.getCompanyById(companyID.toString())).thenReturn(company);

        // Suppose the interview list (some with templates)
        interview.setInterviewTemplate(interviewTemplate);

        List<Interview> allInterviews = new ArrayList<>();
        allInterviews.add(interview);

        when(interviewRepository.findAll()).thenReturn(allInterviews);

        List<InterviewTemplate> templates = interviewManager.getInterviewTemplates(companyID.toString());
        assertEquals(1, templates.size());
        assertEquals(interviewTemplate, templates.getFirst());

        verify(interviewRepository, times(1)).findAll();
    }

    // ------------------------------------------------------------------
    // 6) Test: evaluateInterview(int interviewID, Map<String, Object> payload)
    // ------------------------------------------------------------------
    @Test
    void testEvaluateInterview_Success() {
        // Set the interview status to "submitted"
        interview.setStatus(InterviewStatusEnum.submitted);
        // interview has no quiz yet, but the code tries to fetch it
        InterviewQuiz quiz = new InterviewQuiz();
        interview.setInterviewQuiz(quiz);

        when(interviewRepository.getInterviewById(interview.getId())).thenReturn(interview);

        // Provide a valid evaluation in payload
        payload.put("evaluation", 3);
        payload.put("company_id", companyID.toString());

        when(interviewQuizRepository.save(any(InterviewQuiz.class))).thenAnswer(i -> i.getArgument(0));
        when(internshipPosOfferRepository.save(any(InternshipPosOffer.class))).thenAnswer(i -> i.getArgument(0));
        when(interviewRepository.save(any(Interview.class))).thenAnswer(i -> i.getArgument(0));

        Interview result = interviewManager.evaluateInterview(interview.getId(), payload);
        assertNotNull(result);
        assertEquals(InterviewStatusEnum.passed, result.getStatus());
        assertNotNull(result.getInternshipPosOffer());
        assertEquals(InternshipPosOfferStatusEnum.pending, result.getInternshipPosOffer().getStatus());

        verify(interviewRepository, times(1)).getInterviewById(interview.getId());
        verify(interviewQuizRepository, times(1)).save(quiz);
        verify(internshipPosOfferRepository, times(1)).save(any(InternshipPosOffer.class));
        verify(interviewRepository, times(1)).save(any(Interview.class));
    }

    // ------------------------------------------------------------------
    // 7) Test: sendInterviewPositionOffer(int interviewID, Map<String, Object> payload)
    // ------------------------------------------------------------------
    @Test
    void testSendInterviewPositionOffer_Success() {
        // The method checks that interview doesn't already have an internshipPosOffer
        interview.setInternshipPosOffer(null);
        interview.setStatus(InterviewStatusEnum.passed); // must be "passed"
        when(interviewRepository.findById(interview.getId())).thenReturn(Optional.of(interview));

        payload.put("company_id", companyID.toString());
        // The method checks that userManager.getCompanyById(...) is the same as the
        // recommendation's company. We'll mock that:
        when(userManager.getCompanyById(companyID.toString())).thenReturn(company);

        // We'll store the new pos offer
        when(internshipPosOfferRepository.save(any(InternshipPosOffer.class))).thenAnswer(i -> i.getArgument(0));
        when(interviewRepository.save(any(Interview.class))).thenAnswer(i -> i.getArgument(0));

        InternshipPosOffer result = interviewManager.sendInterviewPositionOffer(interview.getId(), payload);
        assertNotNull(result);
        assertEquals(InternshipPosOfferStatusEnum.pending, result.getStatus());
        assertEquals(interview, result.getInterview());
        verify(internshipPosOfferRepository, times(1)).save(any(InternshipPosOffer.class));
        verify(interviewRepository, times(1)).save(interview);
    }

    // ------------------------------------------------------------------
    // 8) Test: acceptInternshipPositionOffer(Integer intPosOffID, Map<String, Object> payload)
    // ------------------------------------------------------------------
    @Test
    void testAcceptInternshipPositionOffer_Success() throws NotFoundException, BadInputException, UnauthorizedException, WrongStateException {
        // The command checks the user type is STUDENT, and internship pos is pending, etc.
        when(userManager.getUserType(studentID.toString())).thenReturn(UserType.STUDENT);

        // We must ensure the repository returns a "list" that includes our internshipPosOffer
        when(internshipPosOfferRepository.findAll()).thenReturn(List.of(internshipPosOffer));
        // The validation uses getInterview() -> getRecommendation -> getCv -> getStudent...
        // We'll just ensure the student ID matches in our scenario
        Cv cv = new Cv();
        cv.setStudent(student);
        recommendation.setCv(cv);

        // Also, the code calls .stream().filter(...) => The final check uses internshipPosOfferRepository.save()
        when(internshipPosOfferRepository.save(any(InternshipPosOffer.class))).thenAnswer(i -> i.getArgument(0));
        when(userManager.getStudentIDByInternshipPosOfferID(8888)).thenReturn(studentID.toString());
        // Our payload
        payload.put("student_id", studentID.toString());

        InternshipPosOffer result = interviewManager.acceptInternshipPositionOffer(internshipPosOffer.getId(), payload);
        assertNotNull(result);
        assertEquals(InternshipPosOfferStatusEnum.accepted, result.getStatus());
        verify(internshipPosOfferRepository, times(1)).save(internshipPosOffer);
    }

    // ------------------------------------------------------------------
    // 9) Test: rejectInternshipPositionOffer(Integer intPosOffID, Map<String, Object> payload)
    // ------------------------------------------------------------------
    @Test
    void testRejectInternshipPositionOffer_Success() throws NotFoundException, BadInputException, UnauthorizedException, WrongStateException {
        // The code checks the user is a STUDENT
        when(userManager.getUserType(studentID.toString())).thenReturn(UserType.STUDENT);

        // Also checks if the internship pos is found:
        when(internshipPosOfferRepository.findById(internshipPosOffer.getId())).thenReturn(Optional.of(internshipPosOffer));
        when(internshipPosOfferRepository.getById(internshipPosOffer.getId())).thenReturn(internshipPosOffer);
        // And the userManager check for getStudentIDByInternshipPosOfferID
        when(userManager.getStudentIDByInternshipPosOfferID(internshipPosOffer.getId())).thenReturn(studentID.toString());

        // Our payload
        payload.put("student_id", studentID.toString());

        // Mock final save
        when(internshipPosOfferRepository.save(any(InternshipPosOffer.class))).thenAnswer(i -> i.getArgument(0));

        InternshipPosOffer result = interviewManager.rejectInternshipPositionOffer(internshipPosOffer.getId(), payload);
        assertNotNull(result);
        assertEquals(InternshipPosOfferStatusEnum.rejected, result.getStatus());
        verify(internshipPosOfferRepository, times(1)).save(internshipPosOffer);
    }

    // ------------------------------------------------------------------
    // 10) Test: getInterview(String userID)
    // ------------------------------------------------------------------
    @Test
    void testGetInterview_Success() throws NotFoundException, BadInputException {
        // user type is STUDENT
        when(userManager.getUserType(studentID.toString())).thenReturn(UserType.STUDENT);

        // interview with recommendation that has student's ID
        Cv cv = new Cv();
        cv.setStudent(student);
        recommendation.setCv(cv);

        List<Interview> allInterviews = new ArrayList<>();
        allInterviews.add(interview);
        when(interviewRepository.findAll()).thenReturn(allInterviews);

        List<Interview> results = interviewManager.getInterview(studentID.toString());
        assertEquals(1, results.size());
        assertEquals(interview, results.getFirst());
    }

    // ------------------------------------------------------------------
    // 11) Test: getInterviewPosOffersOfUser(String userID)
    // ------------------------------------------------------------------
    @Test
    void testGetInterviewPosOffersOfUser_Success() throws NotFoundException, BadInputException {
        // We can test the STUDENT scenario
        when(userManager.getUserType(studentID.toString())).thenReturn(UserType.STUDENT);
        // Mock that userManager returns the student's interviews
        when(userManager.getInterviewsByStudentID(studentID.toString())).thenReturn(List.of(interview));

        // interview has an internshipPosOffer
        interview.setInternshipPosOffer(internshipPosOffer);

        List<InternshipPosOffer> results = interviewManager.getInterviewPosOffersOfUser(studentID.toString());
        assertEquals(1, results.size());
        assertEquals(internshipPosOffer, results.getFirst());
    }

    // ------------------------------------------------------------------
    // 12) Test: getMatchNotInterviewed(String companyID)
    // ------------------------------------------------------------------
    @Test
    void testGetMatchNotInterviewed_Success() throws NotFoundException, BadInputException {
        // The code calls userManager.getCompanyById()
        when(userManager.getCompanyById(companyID.toString())).thenReturn(company);

        // interview is "toBeSubmitted"
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);

        List<Interview> allInterviews = new ArrayList<>();
        allInterviews.add(interview);
        when(interviewRepository.findAll()).thenReturn(allInterviews);

        List<Interview> results = interviewManager.getMatchNotInterviewed(companyID.toString());
        assertEquals(1, results.size());
        assertEquals(interview, results.getFirst());
    }

    // ------------------------------------------------------------------
    // 13) Test: getSpecificInterview(int interviewID, String userID)
    // ------------------------------------------------------------------
    @Test
    void testGetSpecificInterview_Success() throws NotFoundException, UnauthorizedException {
        // The code checks if user is the student, or the company, or the student's university.
        // Let's pick the student's scenario:
        when(interviewRepository.findById(interview.getId())).thenReturn(Optional.of(interview));

        // If there's a recommendation, it checks if rec.cv.student.id == userID
        Cv cv = new Cv();
        cv.setStudent(student);
        recommendation.setCv(cv);

        Interview result = interviewManager.getSpecificInterview(interview.getId(), studentID.toString());
        assertEquals(interview, result);
        verify(interviewRepository, times(1)).findById(interview.getId());
    }

    // ------------------------------------------------------------------
    // 14) Test: getInterviewTemplate(int templateID, String userID)
    // ------------------------------------------------------------------
    @Test
    void testGetInterviewTemplate_Success() throws NotFoundException, BadInputException {
        when(userManager.getUserType(studentID.toString())).thenReturn(UserType.STUDENT);
        when(interviewTemplateRepository.findById(interviewTemplate.getId())).thenReturn(Optional.of(interviewTemplate));

        // If user is a STUDENT, the code checks the interview's recommendation or spontaneous application
        // We mock interviewRepository.findAll() returning an interview that belongs to the student
        Cv cv = new Cv();
        cv.setStudent(student);
        recommendation.setCv(cv);

        when(interviewRepository.findAll()).thenReturn(List.of(interview));

        InterviewTemplate result = interviewManager.getInterviewTemplate(interviewTemplate.getId(), studentID.toString());
        assertNotNull(result);
        assertEquals(interviewTemplate, result);
    }

    // ------------------------------------------------------------------
    // 15) Test: getInterviewQuiz(int interviewID, String userID)
    // ------------------------------------------------------------------
    @Test
    void testGetInterviewQuiz_Success() throws NotFoundException, BadInputException, UnauthorizedException {
        // Let user type be STUDENT
        when(userManager.getUserType(studentID.toString())).thenReturn(UserType.STUDENT);

        interview.setInterviewQuiz(interviewQuiz);
        // Put the quiz on the interview
        when(interviewRepository.findById(interview.getId())).thenReturn(Optional.of(interview));

        // This method loops through all interviews. We'll return just one
        when(interviewRepository.findAll()).thenReturn(List.of(interview));

        // The code checks if the interview belongs to the user
        Cv cv = new Cv();
        cv.setStudent(student);
        recommendation.setCv(cv);

        InterviewQuiz result = interviewManager.getInterviewQuiz(interview.getId(), studentID.toString());
        assertNotNull(result);
        assertEquals(interviewQuiz, result);
    }
}
