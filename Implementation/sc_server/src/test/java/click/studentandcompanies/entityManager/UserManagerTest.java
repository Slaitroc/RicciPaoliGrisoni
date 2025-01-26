/*
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

public class UserManagerTest {

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

    // --------------------------------------------------------------------
    // 1. Test CRUD & Simple Methods
    // --------------------------------------------------------------------

    */
/*@Test
    void testSaveUniversity() {
        University mockUni = new University();
        mockUni.setId(1);
        mockUni.setName("Mock University");

        when(universityRepository.save(mockUni)).thenReturn(mockUni);

        University saved = userManager.saveUniversity(mockUni);
        assertNotNull(saved);
        assertEquals("Mock University", saved.getName());
        verify(universityRepository, times(1)).save(mockUni);
    }

    @Test
    void testDeleteUniversity() {
        doNothing().when(universityRepository).deleteById(10);
        userManager.deleteUniversity(10);
        verify(universityRepository, times(1)).deleteById(10);
    }

    @Test
    void testGetUniversityByName() {
        University mockUni = new University();
        mockUni.setName("TestName");

        when(universityRepository.findByName("TestName")).thenReturn(mockUni);

        University result = userManager.getUniversityByName("TestName");
        assertNotNull(result);
        assertEquals("TestName", result.getName());
        verify(universityRepository).findByName("TestName");
    }

    // --------------------------------------------------------------------
    // 2. Test logica "getNumberOfUniversitiesByCountry" e "areThereAnyUniversities"
    // --------------------------------------------------------------------

    @Test
    void testGetNumberOfUniversitiesByCountry() {
        // Esempio: se il country è "Italy", verrà passato "It" a countUniversitiesByCountry
        when(universityRepository.countUniversitiesByCountry("It")).thenReturn(5L);

        long result = userManager.getNumberOfUniversitiesByCountry("Italy");
        assertEquals(5L, result);

        verify(universityRepository).countUniversitiesByCountry("It");
    }

    @Test
    void testGetNumberOfUniversities() {
        when(universityRepository.countAll()).thenReturn(10L);
        long result = userManager.getNumberOfUniversities();
        assertEquals(10L, result);
        verify(universityRepository).countAll();
    }

    @Test
    void testAreThereAnyUniversities_true() {
        when(universityRepository.countAll()).thenReturn(1L);
        assertTrue(userManager.areThereAnyUniversities());
        verify(universityRepository).countAll();
    }

    @Test
    void testAreThereAnyUniversities_false() {
        when(universityRepository.countAll()).thenReturn(0L);
        assertFalse(userManager.areThereAnyUniversities());
        verify(universityRepository).countAll();
    }

    // --------------------------------------------------------------------
    // 3. Test getStudentById, getCompanyById, getUniversityById
    // --------------------------------------------------------------------

    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setId(1);
        student.setName("Test Student");
        when(studentRepository.getStudentById(1)).thenReturn(student);

        Student result = userManager.getStudentById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Student", result.getName());
    }

    @Test
    void testGetCompanyById() {
        Company company = new Company();
        company.setId(2);
        company.setName("Acme Inc.");
        when(companyRepository.getCompanyById(2)).thenReturn(company);

        Company result = userManager.getCompanyById(2);
        assertNotNull(result);
        assertEquals("Acme Inc.", result.getName());
    }

    @Test
    void testGetUniversityById() {
        University uni = new University();
        uni.setId(3);
        uni.setName("Test University");
        when(universityRepository.getUniversityById(3)).thenReturn(uni);

        University result = userManager.getUniversityById(3);
        assertNotNull(result);
        assertEquals("Test University", result.getName());
    }

    // --------------------------------------------------------------------
    // 4. Test getUserType
    // --------------------------------------------------------------------

    @Test
    void testGetUserType_Student() {
        Student student = new Student();
        student.setId(1);
        when(studentRepository.getStudentById(1)).thenReturn(student);
        UserType result = userManager.getUserType(1);
        assertEquals(UserType.STUDENT, result);
    }

    @Test
    void testGetUserType_Company() {
        Company company = new Company();
        company.setId(2);
        when(companyRepository.getCompanyById(2)).thenReturn(company);
        UserType result = userManager.getUserType(2);
        assertEquals(UserType.COMPANY, result);
    }

    @Test
    void testGetUserType_University() {
        University uni = new University();
        uni.setId(3);
        when(universityRepository.getUniversityById(3)).thenReturn(uni);
        UserType result = userManager.getUserType(3);
        assertEquals(UserType.UNIVERSITY, result);
    }

    @Test
    void testGetUserType_Unknown() {
        // Nessun student, company o university con ID = 999
        when(studentRepository.getStudentById(999)).thenReturn(null);
        when(companyRepository.getCompanyById(999)).thenReturn(null);
        when(universityRepository.getUniversityById(999)).thenReturn(null);

        UserType result = userManager.getUserType(999);
        assertEquals(UserType.UNKNOWN, result);
    }

    // --------------------------------------------------------------------
    // 5. Test getParticipantType
    // --------------------------------------------------------------------

    @Test
    void testGetParticipantType_Student() {
        Student student = new Student();
        student.setId(10);
        when(studentRepository.getStudentById(10)).thenReturn(student);
        assertEquals(ParticipantTypeEnum.student, userManager.getParticipantType(10));
    }

    @Test
    void testGetParticipantType_Company() {
        Company company = new Company();
        company.setId(20);
        when(companyRepository.getCompanyById(20)).thenReturn(company);
        assertEquals(ParticipantTypeEnum.company, userManager.getParticipantType(20));
    }

    @Test
    void testGetParticipantType_Null() {
        // Non esiste student, non esiste company
        when(studentRepository.getStudentById(999)).thenReturn(null);
        when(companyRepository.getCompanyById(999)).thenReturn(null);
        assertNull(userManager.getParticipantType(999));
    }

    // --------------------------------------------------------------------
    // 6. Test Recommendation Methods
    // --------------------------------------------------------------------

    @Test
    void testGetRecommendationByStudentId() {
        Recommendation rec1 = new Recommendation();
        rec1.setId(101);
        Recommendation rec2 = new Recommendation();
        rec2.setId(102);

        when(recommendationRepository.findRecommendationByStudentId(1))
                .thenReturn(List.of(rec1, rec2));

        List<Recommendation> result = userManager.getRecommendationByStudentId(1);
        assertEquals(2, result.size());
        assertTrue(result.contains(rec1));
        assertTrue(result.contains(rec2));
    }

    @Test
    void testGetRecommendationByCompanyId() {
        Recommendation rec = new Recommendation();
        rec.setId(201);

        when(recommendationRepository.findRecommendationByCompanyId(2))
                .thenReturn(List.of(rec));

        List<Recommendation> result = userManager.getRecommendationByCompanyId(2);
        assertEquals(1, result.size());
        assertEquals(201, result.get(0).getId());
    }

    @Test
    void testGetRecommendationById() {
        Recommendation rec = new Recommendation();
        rec.setId(42);

        when(recommendationRepository.getRecommendationById(42)).thenReturn(rec);

        Recommendation result = userManager.getRecommendationById(42);
        assertNotNull(result);
        assertEquals(42, result.getId());
    }

    // --------------------------------------------------------------------
    // 7. Test getInvolvedUsers
    // --------------------------------------------------------------------

    /*@Test
    void testGetInvolvedUsers() {
        // Spontaneous Applications
        Student studentA = new Student();
        studentA.setId(1);
        SpontaneousApplication appA = new SpontaneousApplication();
        appA.setStudent(studentA);

        Student studentB = new Student();
        studentB.setId(2);
        SpontaneousApplication appB = new SpontaneousApplication();
        appB.setStudent(studentB);

        when(spontaneousApplicationRepository.findAllByInternshipOfferId(100))
                .thenReturn(List.of(appA, appB));

        // Recommendations
        Cv cvC = new Cv();
        Student studentC = new Student();
        studentC.setId(3);
        cvC.setStudent(studentC);
        Recommendation recC = new Recommendation();
        recC.setCv(cvC);

        Cv cvD = new Cv();
        Student studentD = new Student();
        studentD.setId(4);
        cvD.setStudent(studentD);
        Recommendation recD = new Recommendation();
        recD.setCv(cvD);

        when(recommendationRepository.findRecommendationByInternshipOfferId(100))
                .thenReturn(List.of(recC, recD));

        List<Integer> result = userManager.getInvolvedUsers(100);

        assertEquals(4, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
    }*//*


    // --------------------------------------------------------------------
    // 8. Test InternshipOffer & other repositories
    // --------------------------------------------------------------------

    @Test
    void testGetInternshipOfferById() {
        InternshipOffer io = new InternshipOffer();
        io.setId(1000);
        when(internshipOfferRepository.getInternshipOfferById(1000)).thenReturn(io);

        InternshipOffer result = userManager.getInternshipOfferById(1000);
        assertNotNull(result);
        assertEquals(1000, result.getId());
    }

    @Test
    void testGetAllInternshipOffers() {
        InternshipOffer io1 = new InternshipOffer();
        InternshipOffer io2 = new InternshipOffer();

        when(internshipOfferRepository.findAll()).thenReturn(List.of(io1, io2));

        List<InternshipOffer> result = userManager.getAllInternshipOffers();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllCvs() {
        Cv cv1 = new Cv();
        Cv cv2 = new Cv();
        when(cvRepository.findAll()).thenReturn(List.of(cv1, cv2));

        List<Cv> result = userManager.getAllCvs();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllFeedbacks() {
        Feedback fb1 = new Feedback();
        Feedback fb2 = new Feedback();
        when(feedbackRepository.findAll()).thenReturn(List.of(fb1, fb2));

        List<Feedback> result = userManager.getAllFeedbacks();
        assertEquals(2, result.size());
    }

    // --------------------------------------------------------------------
    // 9. Test getStudentIDByInternshipPosOfferID
    // --------------------------------------------------------------------

    @Test
    void testGetStudentIDByInternshipPosOfferID_RecommendationNotNull() {
        // Caso 1: se c'è la Recommendation, restituisce l'ID dello student associato
        Student student = new Student();
        student.setId(77);

        Cv cv = new Cv();
        cv.setStudent(student);

        Recommendation recommendation = new Recommendation();
        recommendation.setCv(cv);

        Interview interview = new Interview();
        interview.setRecommendation(recommendation);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(500))
                .thenReturn(interview);

        Integer result = userManager.getStudentIDByInternshipPosOfferID(500);
        assertEquals(77, result);
    }

    @Test
    void testGetStudentIDByInternshipPosOfferID_RecommendationNull() {
        // Caso 2: se la Recommendation è null, prende lo studente da SpontaneousApplication
        Student student = new Student();
        student.setId(99);

        SpontaneousApplication app = new SpontaneousApplication();
        app.setStudent(student);

        Interview interview = new Interview();
        interview.setSpontaneousApplication(app);

        when(interviewRepository.getInterviewByInternshipPosOffer_Id(501))
                .thenReturn(interview);

        Integer result = userManager.getStudentIDByInternshipPosOfferID(501);
        assertEquals(99, result);
    }
}*/
