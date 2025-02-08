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
 * Extended test class for UserManager
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
    @Mock
    private InternshipPosOfferRepository internshipPosOfferRepository;

    @InjectMocks
    private UserManager userManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------------------------------------------------
    // Existing tests
    // ------------------------------------------------------------------

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
        SpontaneousApplication appA = new SpontaneousApplication();
        appA.setStudent(studentA);
        Student studentB = setNewStudent(2, "Carol", setNewUniversity(11, "UniB", "FR"));
        SpontaneousApplication appB = new SpontaneousApplication();
        appB.setStudent(studentB);
        when(spontaneousApplicationRepository.findAllByInternshipOfferId(100)).thenReturn(List.of(appA, appB));

        // Recommendations
        Cv cvC = setNewCv(setNewStudent(3, "Eve", setNewUniversity(12, "UniC", "DE")));
        Recommendation recC = new Recommendation();
        recC.setCv(cvC);

        Cv cvD = setNewCv(setNewStudent(4, "Dan", setNewUniversity(13, "UniD", "ES")));
        Recommendation recD = new Recommendation();
        recD.setCv(cvD);

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
        InternshipOffer io = setNewInternshipOffer(setNewCompany(33, "MockCo", "IT"));
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
        Cv cv1 = new Cv();
        Cv cv2 = new Cv();
        when(cvRepository.findAll()).thenReturn(List.of(cv1, cv2));
        List<Cv> cvs = userManager.getAllCvs();
        assertEquals(2, cvs.size());

        // Feedback
        Feedback fb1 = new Feedback();
        Feedback fb2 = new Feedback();
        when(feedbackRepository.findAll()).thenReturn(List.of(fb1, fb2));
        List<Feedback> fbs = userManager.getAllFeedbacks();
        assertEquals(2, fbs.size());
    }

    @Test
    void testGetStudentIDByInternshipPosOfferID() {
        // Recommendation not null
        Student s1 = setNewStudent(77, "TestStudent", setNewUniversity(1, "Uni", "IT"));
        Cv cv = setNewCv(s1);
        Recommendation recommendation = new Recommendation();
        recommendation.setCv(cv);
        Interview interview1 = new Interview();
        interview1.setRecommendation(recommendation);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(500)).thenReturn(interview1);
        String res1 = userManager.getStudentIDByInternshipPosOfferID(500);
        assertEquals("77", res1);

        // Recommendation null
        Student s2 = setNewStudent(99, "John", setNewUniversity(2, "UniX", "FR"));
        SpontaneousApplication app = new SpontaneousApplication();
        app.setStudent(s2);
        Interview interview2 = new Interview();
        interview2.setSpontaneousApplication(app);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(501)).thenReturn(interview2);
        String res2 = userManager.getStudentIDByInternshipPosOfferID(501);
        assertEquals("99", res2);
    }


    @Test
    void testGetCompanyIDByInternshipPosOfferID_withRecommendation() {
        // Scenario: Interview has a Recommendation => use recommendation.internshipOffer.company
        Company comp = setNewCompany(11, "RecCompany", "IT");
        InternshipOffer offer = setNewInternshipOffer(111, comp);
        Recommendation rec = new Recommendation();
        rec.setInternshipOffer(offer);
        Interview interview = new Interview();
        interview.setRecommendation(rec);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(100)).thenReturn(interview);

        String companyID = userManager.getCompanyIDByInternshipPosOfferID(100);
        assertEquals("11", companyID);
    }

    @Test
    void testGetCompanyIDByInternshipPosOfferID_withSpontaneousApplication() {
        // Scenario: Interview has a SpontaneousApplication => use spontaneous.internshipOffer.company
        Company comp = setNewCompany(22, "SpontCompany", "FR");
        InternshipOffer offer = setNewInternshipOffer(222, comp);
        SpontaneousApplication app = new SpontaneousApplication();
        app.setInternshipOffer(offer);

        Interview interview = new Interview();
        interview.setSpontaneousApplication(app);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(200)).thenReturn(interview);

        String companyID = userManager.getCompanyIDByInternshipPosOfferID(200);
        assertEquals("22", companyID);
    }

    @Test
    void testGetUniversity() {
        University u1 = setNewUniversity(101, "MyUni1", "IT");
        University u2 = setNewUniversity(102, "MyUni2", "US");
        when(universityRepository.findAll()).thenReturn(List.of(u1, u2));

        List<University> unis = userManager.getUniversity();
        assertEquals(2, unis.size());
        assertEquals("MyUni1", unis.get(0).getName());
        assertEquals("MyUni2", unis.get(1).getName());
        verify(universityRepository, times(1)).findAll();
    }

    @Test
    void testGetCompany() {
        Company c1 = setNewCompany(201, "Comp1", "IT");
        Company c2 = setNewCompany(202, "Comp2", "UK");
        when(companyRepository.findAll()).thenReturn(List.of(c1, c2));

        List<Company> comps = userManager.getCompany();
        assertEquals(2, comps.size());
        assertEquals("Comp1", comps.get(0).getName());
        assertEquals("Comp2", comps.get(1).getName());
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testGetInterviewsByStudentID() {
        // We'll just make a few Interviews and filter by Student ID
        Student s1 = setNewStudent(1, "Alice", setNewUniversity(10, "UniA", "IT"));
        Student s2 = setNewStudent(2, "Bob", setNewUniversity(11, "UniB", "FR"));

        Interview i1 = new Interview();
        Recommendation rec1 = new Recommendation();
        Cv cv1 = setNewCv(s1);
        rec1.setCv(cv1);
        i1.setRecommendation(rec1);

        Interview i2 = new Interview();
        SpontaneousApplication app2 = new SpontaneousApplication();
        app2.setStudent(s2);
        i2.setSpontaneousApplication(app2);

        Interview i3 = new Interview();
        SpontaneousApplication app3 = new SpontaneousApplication();
        app3.setStudent(s1);
        i3.setSpontaneousApplication(app3);

        when(interviewRepository.findAll()).thenReturn(List.of(i1, i2, i3));

        List<Interview> resultForS1 = userManager.getInterviewsByStudentID("1");
        assertEquals(2, resultForS1.size());
        assertSame(i1, resultForS1.get(0));
        assertSame(i3, resultForS1.get(1));

        List<Interview> resultForS2 = userManager.getInterviewsByStudentID("2");
        assertEquals(1, resultForS2.size());
        assertSame(i2, resultForS2.getFirst());
    }

    @Test
    void testGetInterviewsByCompanyID() {
        Company compA = setNewCompany(100, "CompA", "IT");
        Company compB = setNewCompany(200, "CompB", "FR");

        // i1 => belongs to compA via Recommendation
        Interview i1 = new Interview();
        Recommendation rec1 = new Recommendation();
        InternshipOffer offer1 = setNewInternshipOffer(111, compA);
        rec1.setInternshipOffer(offer1);
        i1.setRecommendation(rec1);

        // i2 => belongs to compA via SpontaneousApplication
        Interview i2 = new Interview();
        SpontaneousApplication sa2 = new SpontaneousApplication();
        InternshipOffer offer2 = setNewInternshipOffer(222, compA);
        sa2.setInternshipOffer(offer2);
        i2.setSpontaneousApplication(sa2);

        // i3 => belongs to compB via Recommendation
        Interview i3 = new Interview();
        Recommendation rec3 = new Recommendation();
        InternshipOffer offer3 = setNewInternshipOffer(333, compB);
        rec3.setInternshipOffer(offer3);
        i3.setRecommendation(rec3);

        when(interviewRepository.findAll()).thenReturn(List.of(i1, i2, i3));

        List<Interview> resultCompA = userManager.getInterviewsByCompanyID("100");
        assertEquals(2, resultCompA.size());
        assertTrue(resultCompA.contains(i1));
        assertTrue(resultCompA.contains(i2));

        List<Interview> resultCompB = userManager.getInterviewsByCompanyID("200");
        assertEquals(1, resultCompB.size());
        assertTrue(resultCompB.contains(i3));
    }

    @Test
    void testGetInternshipPosOfferById() {
        InternshipPosOffer ipo = new InternshipPosOffer();
        ipo.setId(9999);
        when(internshipPosOfferRepository.getInternshipPosOfferById(9999)).thenReturn(ipo);

        InternshipPosOffer result = userManager.getInternshipPosOfferById(9999);
        assertEquals(9999, result.getId());
        verify(internshipPosOfferRepository).getInternshipPosOfferById(9999);
    }

    @Test
    void testSaveInterview() {
        Interview interview = new Interview();
        interview.setId(555);
        userManager.saveInterview(interview);
        verify(interviewRepository, times(1)).save(interview);
    }
}
