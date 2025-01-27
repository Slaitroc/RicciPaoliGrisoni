package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entityRepository.*;
import click.studentandcompanies.utils.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Condensed test style for UserManager, grouping multiple scenarios into fewer test methods.
 */
class UserManagerTest extends EntityFactory {

    @Mock
    private UniversityRepository universityRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private RecommendationRepository recommendationRepository;
    @Mock
    private SpontaneousApplicationRepository spontaneousApplicationRepository;
    @Mock
    private InternshipOfferRepository internshipOfferRepository;
    @Mock
    private CvRepository cvRepository;
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private InterviewRepository interviewRepository;

    @InjectMocks
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUniversityOperations() {
        // 1) Save University
        University uni = setNewUniversity(1, "Mock University", "IT");
        when(universityRepository.save(uni)).thenReturn(uni);
        University saved = userManager.saveUniversity(uni);
        assertNotNull(saved);
        assertEquals("Mock University", saved.getName());
        verify(universityRepository).save(uni);

        // 2) Delete University
        doNothing().when(universityRepository).deleteById("10");
        userManager.deleteUniversity("10");
        verify(universityRepository).deleteById("10");

        // 3) getUniversityByName
        University foundByName = setNewUniversity(2, "TestName", "IT");
        when(universityRepository.findByName("TestName")).thenReturn(foundByName);
        University result = userManager.getUniversityByName("TestName");
        assertNotNull(result);
        assertEquals("TestName", result.getName());
        verify(universityRepository).findByName("TestName");

        // 4) getNumberOfUniversitiesByCountry
        when(universityRepository.countUniversitiesByCountry("It")).thenReturn(5L);
        long numIT = userManager.getNumberOfUniversitiesByCountry("Italy");
        assertEquals(5L, numIT);
        verify(universityRepository).countUniversitiesByCountry("It");

        // 5) getNumberOfUniversities
        when(universityRepository.countAll()).thenReturn(10L);
        long totalUnis = userManager.getNumberOfUniversities();
        assertEquals(10L, totalUnis);
        verify(universityRepository).countAll();

        // 6) areThereAnyUniversities
        when(universityRepository.countAll()).thenReturn(0L);
        assertFalse(userManager.areThereAnyUniversities());
        when(universityRepository.countAll()).thenReturn(1L);
        assertTrue(userManager.areThereAnyUniversities());
    }

    @Test
    void testGetEntitiesById() {
        // Student
        Student student = setNewStudent(10, "Alice", setNewUniversity(1, "UniA", "IT"));
        when(studentRepository.getStudentById("10")).thenReturn(student);
        Student resultStudent = userManager.getStudentById("10");
        assertNotNull(resultStudent);
        assertEquals("Alice", resultStudent.getName());

        // Company
        Company company = setNewCompany(20, "Acme Inc.", "US");
        when(companyRepository.getCompanyById("20")).thenReturn(company);
        Company resultCompany = userManager.getCompanyById("20");
        assertNotNull(resultCompany);
        assertEquals("Acme Inc.", resultCompany.getName());

        // University
        University uni = setNewUniversity(30, "UniX", "FR");
        when(universityRepository.getUniversityById("30")).thenReturn(uni);
        University resultUni = userManager.getUniversityById("30");
        assertNotNull(resultUni);
        assertEquals("UniX", resultUni.getName());
    }

    @Test
    void testGetUserType() {
        // Student
        Student s = setNewStudent(1, "Bob", setNewUniversity(99, "UniB", "IT"));
        when(studentRepository.getStudentById("1")).thenReturn(s);
        assertEquals(UserType.STUDENT, userManager.getUserType("1"));

        // Company
        Company c = setNewCompany(2, "Company", "IT");
        when(companyRepository.getCompanyById("2")).thenReturn(c);
        assertEquals(UserType.COMPANY, userManager.getUserType("2"));

        // University
        University u = setNewUniversity(3, "Uni", "IT");
        when(universityRepository.getUniversityById("3")).thenReturn(u);
        assertEquals(UserType.UNIVERSITY, userManager.getUserType("3"));

        // Unknown
        when(studentRepository.getStudentById("999")).thenReturn(null);
        when(companyRepository.getCompanyById("999")).thenReturn(null);
        when(universityRepository.getUniversityById("999")).thenReturn(null);
        assertEquals(UserType.UNKNOWN, userManager.getUserType("999"));
    }

    @Test
    void testGetParticipantType() {
        // Student
        Student stud = setNewStudent(10, "Eve", setNewUniversity(100, "UniE", "DE"));
        when(studentRepository.getStudentById("10")).thenReturn(stud);
        assertEquals(ParticipantTypeEnum.student, userManager.getParticipantType("10"));

        // Company
        Company comp = setNewCompany(20, "TechCo", "UK");
        when(companyRepository.getCompanyById("20")).thenReturn(comp);
        assertEquals(ParticipantTypeEnum.company, userManager.getParticipantType("20"));

        // Null
        when(studentRepository.getStudentById("999")).thenReturn(null);
        when(companyRepository.getCompanyById("999")).thenReturn(null);
        assertNull(userManager.getParticipantType("999"));
    }

    @Test
    void testRecommendationRetrieval() {
        // By studentId
        Recommendation rec1 = new Recommendation(); rec1.setId(101);
        Recommendation rec2 = new Recommendation(); rec2.setId(102);
        when(recommendationRepository.findRecommendationByStudentId("1")).thenReturn(List.of(rec1, rec2));
        List<Recommendation> recsByStudent = userManager.getRecommendationByStudentId("1");
        assertEquals(2, recsByStudent.size());

        // By companyId
        Recommendation recC = new Recommendation(); recC.setId(201);
        when(recommendationRepository.findRecommendationByCompanyId("2")).thenReturn(List.of(recC));
        List<Recommendation> recsByCompany = userManager.getRecommendationByCompanyId("2");
        assertEquals(1, recsByCompany.size());
        assertEquals(201, recsByCompany.getFirst().getId());

        // By recommendationId
        Recommendation rec42 = new Recommendation(); rec42.setId(42);
        when(recommendationRepository.getRecommendationById(42)).thenReturn(rec42);
        Recommendation recById = userManager.getRecommendationById(42);
        assertNotNull(recById);
        assertEquals(42, recById.getId());
    }

    @Test
    void testGetInvolvedUsers() {
        // Applications
        Student studentA = setNewStudent(1, "Bob", setNewUniversity(10, "UniA", "IT"));
        SpontaneousApplication appA = new SpontaneousApplication(); appA.setStudent(studentA);
        Student studentB = setNewStudent(2, "Carol", setNewUniversity(11,"UniB","FR"));
        SpontaneousApplication appB = new SpontaneousApplication(); appB.setStudent(studentB);
        when(spontaneousApplicationRepository.findAllByInternshipOfferId(100)).thenReturn(List.of(appA, appB));

        // Recommendations
        Cv cvC = setNewCv(setNewStudent(3, "Eve", setNewUniversity(12,"UniC","DE")));
        Recommendation recC = new Recommendation(); recC.setCv(cvC);
        Cv cvD = setNewCv(setNewStudent(4, "Dan", setNewUniversity(13,"UniD","ES")));
        Recommendation recD = new Recommendation(); recD.setCv(cvD);
        when(recommendationRepository.findRecommendationByInternshipOfferId(100)).thenReturn(List.of(recC, recD));

        List<String> result = userManager.getInvolvedUsers(100);
        assertEquals(4, result.size());
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("4"));
    }

    @Test
    void testOtherRepositories() {
        // InternshipOffer
        InternshipOffer io = setNewInternshipOffer(setNewCompany(33,"MockCo","IT"));
        io.setId(1000);
        when(internshipOfferRepository.getInternshipOfferById(1000)).thenReturn(io);
        InternshipOffer resultIO = userManager.getInternshipOfferById(1000);
        assertNotNull(resultIO);
        assertEquals(1000, resultIO.getId());

        InternshipOffer io1 = new InternshipOffer();
        InternshipOffer io2 = new InternshipOffer();
        when(internshipOfferRepository.findAll()).thenReturn(List.of(io1, io2));
        List<InternshipOffer> offers = userManager.getAllInternshipOffers();
        assertEquals(2, offers.size());

        // CV
        Cv cv1 = new Cv(); Cv cv2 = new Cv();
        when(cvRepository.findAll()).thenReturn(List.of(cv1, cv2));
        List<Cv> cvs = userManager.getAllCvs();
        assertEquals(2, cvs.size());

        // Feedback
        Feedback fb1 = new Feedback(); Feedback fb2 = new Feedback();
        when(feedbackRepository.findAll()).thenReturn(List.of(fb1, fb2));
        List<Feedback> fbs = userManager.getAllFeedbacks();
        assertEquals(2, fbs.size());
    }

    @Test
    void testGetStudentIDByInternshipPosOfferID() {
        // Recommendation not null
        Student s1 = setNewStudent(77, "TestStudent", setNewUniversity(1,"Uni","IT"));
        Cv cv = setNewCv(s1);
        Recommendation recommendation = new Recommendation(); recommendation.setCv(cv);
        Interview interview1 = new Interview(); interview1.setRecommendation(recommendation);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(500)).thenReturn(interview1);
        String res1 = userManager.getStudentIDByInternshipPosOfferID(500);
        assertEquals("77", res1);

        // Recommendation null
        Student s2 = setNewStudent(99, "John", setNewUniversity(2,"UniX","FR"));
        SpontaneousApplication app = new SpontaneousApplication(); app.setStudent(s2);
        Interview interview2 = new Interview(); interview2.setSpontaneousApplication(app);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(501)).thenReturn(interview2);
        String res2 = userManager.getStudentIDByInternshipPosOfferID(501);
        assertEquals("99", res2);
    }
}
