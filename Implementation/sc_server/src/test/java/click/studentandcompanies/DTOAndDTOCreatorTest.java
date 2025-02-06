package click.studentandcompanies;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DTOAndDTOCreatorTest {

    private DTO dto;

    @BeforeEach
    void setUp() {
        dto = new DTO();
    }

    // -------------------------------------------------------------------
    // Tests for DTO
    // -------------------------------------------------------------------

    @Test
    void testDTO_AddAndGetProperty() {
        dto.addProperty("key1", "value1");
        dto.addProperty("key2", 123);
        assertEquals("value1", dto.getProperty("key1"));
        assertEquals(123, dto.getProperty("key2"));
    }

    @Test
    void testDTO_GetProperties() {
        dto.addProperty("k1", "v1");
        dto.addProperty("k2", "v2");
        Map<String, Object> props = dto.getProperties();
        assertEquals(2, props.size());
        assertEquals("v1", props.get("k1"));
        assertEquals("v2", props.get("k2"));
    }

    @Test
    void testDTO_ToString() {
        dto.addProperty("foo", "bar");
        String result = dto.toString();
        assertTrue(result.contains("foo=bar"), "DTO toString() should contain the property set");
        assertTrue(result.startsWith("DTO {"), "Should start with 'DTO {'");
        assertTrue(result.endsWith("}"), "Should end with '}'");
    }

    @Test
    void testDTO_Equals() {
        // same class + same properties => true
        DTO dto2 = new DTO();
        dto.addProperty("x", 1);
        dto2.addProperty("x", 1);
        assertEquals(dto, dto2);

        // same class + different properties => false
        dto2.addProperty("y", 2);
        assertNotEquals(dto, dto2);

        // different class => false
        assertNotEquals("some string", dto);

        // null => false
        assertNotEquals(null, dto);
    }

    // -------------------------------------------------------------------
    // Tests for DTOCreator
    // We create minimal object stubs to avoid NPE and test property addition
    // -------------------------------------------------------------------

    @Test
    void testDTOCreator_CreateErrorDTO() {
        DTO errorDTO = DTOCreator.createDTO(DTOTypes.ERROR, "Something went wrong");
        assertNotNull(errorDTO);
        assertEquals("Something went wrong", errorDTO.getProperty("error"));
    }

    @Test
    void testDTOCreator_CreateCVDTO() {
        // minimal CV
        Cv cv = new Cv();
        cv.setId(1111);
        cv.setCertifications("CertA");
        cv.setEducation("EduB");
        cv.setProject("ProjC");
        cv.setSkills("Java");
        cv.setWorkExperiences("ExpZ");
        cv.setUpdateTime(Instant.now());
        Student st = new Student();
        st.setId("2001");
        st.setName("StudentName");
        cv.setStudent(st);

        DTO cvDTO = DTOCreator.createDTO(DTOTypes.CV, cv);
        assertNotNull(cvDTO);
        assertEquals(1111, cvDTO.getProperty("id"));
        assertEquals("CertA", cvDTO.getProperty("certifications"));
        assertEquals("StudentName", cvDTO.getProperty("studentName"));
    }

    @Test
    void testDTOCreator_CreateAccountDTO() {
        Account account = new Account();
        account.setUserID("acc-123");
        account.setName("John");
        account.setSurname("Doe");
        account.setEmail("john@example.com");
        account.setVatNumber(9999);

        DTO accountDTO = DTOCreator.createDTO(DTOTypes.ACCOUNT, account);
        assertNotNull(accountDTO);
        assertEquals("acc-123", accountDTO.getProperty("userID"));
        assertEquals("John", accountDTO.getProperty("name"));
        assertEquals("Doe", accountDTO.getProperty("surname"));
        assertEquals("john@example.com", accountDTO.getProperty("email"));
        assertEquals(9999, accountDTO.getProperty("vatNumber"));
    }

    @Test
    void testDTOCreator_CreateStudentDTO() {
        Student student = new Student();
        student.setId("S-001");
        student.setName("Alice");
        student.setSurname("Smith");
        student.setEmail("alice@uni.com");
        student.setBirthdate(LocalDate.of(2000,1,1));
        student.setCountry("IT");
        University uni = new University();
        uni.setId("U-111");
        uni.setName("TestUniversity");
        student.setUniversity(uni);

        DTO studentDTO = DTOCreator.createDTO(DTOTypes.STUDENT, student);
        assertNotNull(studentDTO);
        assertEquals("S-001", studentDTO.getProperty("id"));
        assertEquals("Alice", studentDTO.getProperty("name"));
        assertEquals("U-111", studentDTO.getProperty("universityID"));
    }

    @Test
    void testDTOCreator_CreateMessageDTO() {
        Message message = new Message();
        message.setId(777);
        message.setBody("Hello world");
        message.setSenderName("test-sender");

        Communication comm = new Communication();
        comm.setTitle("CommTitle");
        message.setCommunication(comm);

        DTO msgDTO = DTOCreator.createDTO(DTOTypes.MESSAGE, message);
        assertNotNull(msgDTO);
        assertEquals(777, msgDTO.getProperty("id"));
        assertEquals("Hello world", msgDTO.getProperty("body"));
        assertEquals("CommTitle", msgDTO.getProperty("communicationTitle"));
    }

    @Test
    void testDTOCreator_CreateFeedbackDTO() {
        Feedback feedback = new Feedback();
        feedback.setId(4321);
        feedback.setParticipantType(ParticipantTypeEnum.student);
        feedback.setRating(5);

        Recommendation rec = new Recommendation();
        rec.setId(999);
        feedback.setRecommendation(rec);

        Student st = new Student();
        st.setId("stud-333");
        feedback.setStudent(st);

        DTO feedbackDTO = DTOCreator.createDTO(DTOTypes.FEEDBACK, feedback);
        assertNotNull(feedbackDTO);
        assertEquals(4321, feedbackDTO.getProperty("id"));
        assertEquals(ParticipantTypeEnum.student, feedbackDTO.getProperty("participantType"));
        assertEquals("stud-333", feedbackDTO.getProperty("studentID"));
        assertEquals(999, feedbackDTO.getProperty("recommendationID"));
    }

    @Test
    void testDTOCreator_CreateInterviewDTO_withRecommendation() {
        Interview interview = new Interview();
        interview.setId(3000);
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);
        interview.setHasAnswered(false);

        Recommendation recommendation = new Recommendation();
        InternshipOffer offer = new InternshipOffer();
        offer.setId(505);
        offer.setTitle("MyInternshipTitle");
        Company comp = new Company();
        comp.setId("C-202");
        comp.setName("TestCompany");
        offer.setCompany(comp);
        recommendation.setInternshipOffer(offer);

        Cv cv = new Cv();
        Student student = new Student();
        student.setId("Stud-5");
        student.setName("MyStudent");
        cv.setStudent(student);
        recommendation.setCv(cv);
        interview.setRecommendation(recommendation);

        InterviewTemplate interviewTemplate = new InterviewTemplate();
        interviewTemplate.setId(5555);
        interview.setInterviewTemplate(interviewTemplate);

        // no quiz => will show interviewQuizID = null
        DTO interviewDTO = DTOCreator.createDTO(DTOTypes.INTERVIEW, interview);
        assertNotNull(interviewDTO);
        assertEquals(3000, interviewDTO.getProperty("id"));
        assertEquals("MyInternshipTitle", interviewDTO.getProperty("internshipTitle"));
        assertEquals("C-202", interviewDTO.getProperty("companyID"));
        assertEquals("MyStudent", interviewDTO.getProperty("studentName"));
        assertEquals(5555, interviewDTO.getProperty("interviewTemplateID"));
        assertNull(interviewDTO.getProperty("interviewQuizID"));
    }

    @Test
    void testDTOCreator_CreateInterviewDTO_withSpontaneousApplication() {
        // This time we'll populate SpontaneousApplication instead of Recommendation
        Interview interview = new Interview();
        interview.setId(3001);
        interview.setStatus(InterviewStatusEnum.submitted);
        interview.setHasAnswered(true);

        SpontaneousApplication sa = new SpontaneousApplication();
        sa.setId(4321);
        InternshipOffer offer = new InternshipOffer();
        offer.setId(100);
        offer.setTitle("SpontaneousTitle");
        Company comp = new Company();
        comp.setId("C-999");
        comp.setName("SpontCompany");
        offer.setCompany(comp);
        sa.setInternshipOffer(offer);
        Student st = new Student();
        st.setId("S-999");
        st.setName("SpontStudent");
        sa.setStudent(st);

        interview.setSpontaneousApplication(sa);
        // no interviewTemplate, no quiz

        DTO interviewDTO = DTOCreator.createDTO(DTOTypes.INTERVIEW, interview);
        assertEquals("SpontaneousTitle", interviewDTO.getProperty("internshipTitle"));
        assertEquals("C-999", interviewDTO.getProperty("companyID"));
        assertEquals("SpontStudent", interviewDTO.getProperty("studentName"));
        assertTrue((Boolean) interviewDTO.getProperty("hasAnswered"));
    }

    @Test
    void testDTOCreator_CreateUniversityDTO() {
        University university = new University();
        university.setId("U-444");
        university.setName("MyUni");
        university.setCountry("Italy");
        university.setVatNumber(999);
        university.setEmail("uni@example.edu");

        DTO uniDTO = DTOCreator.createDTO(DTOTypes.UNIVERSITY, university);
        assertNotNull(uniDTO);
        assertEquals("U-444", uniDTO.getProperty("id"));
        assertEquals("MyUni", uniDTO.getProperty("name"));
        assertEquals("Italy", uniDTO.getProperty("country"));
    }

    @Test
    void testDTOCreator_CreateCommunicationDTO() {
        Communication comm = new Communication();
        comm.setId(555);
        comm.setCommunicationType(CommunicationTypeEnum.communication);
        comm.setTitle("CommTitle");
        comm.setContent("CommContent");
        comm.setParticipantType(ParticipantTypeEnum.company);

        // If student is null => code uses getCompany()
        Company company = new Company();
        company.setId("Comp-123");
        company.setName("CommunicationCompany");
        comm.setCompany(company);

        // Must also create an internshipPosOff -> interview -> recommendation or spontaneous
        InternshipPosOffer pos = new InternshipPosOffer();
        pos.setStatus(InternshipPosOfferStatusEnum.pending);
        Interview interview = new Interview();
        Recommendation rec = new Recommendation();
        Cv cv = new Cv();
        Student st = new Student();
        st.setId("Stud-321");
        st.setName("CommStudent");
        University uni = new University();
        uni.setId("U-567");
        uni.setName("CommUniversity");
        st.setUniversity(uni);
        cv.setStudent(st);

        InternshipOffer off = new InternshipOffer();
        off.setId(8080);
        off.setTitle("CommOfferTitle");
        Company comp2 = new Company();
        comp2.setId("Comp2");
        comp2.setName("Comp2Name");
        off.setCompany(comp2);

        rec.setCv(cv);
        rec.setInternshipOffer(off);

        interview.setRecommendation(rec);
        pos.setInterview(interview);
        comm.setInternshipPosOff(pos);

        DTO commDTO = DTOCreator.createDTO(DTOTypes.COMMUNICATION, comm);
        assertNotNull(commDTO);
        assertEquals(555, commDTO.getProperty("id"));
        // We only check a few:
        assertEquals("CommStudent", commDTO.getProperty("studentName"));
        assertEquals("CommUniversity", commDTO.getProperty("universityName"));
        assertEquals(8080, commDTO.getProperty("internshipOfferID"));
    }

    @Test
    void testDTOCreator_CreateUniversityMapDTO() {
        Map<String, Integer> map = new HashMap<>();
        map.put("PoliMi", 1000);
        map.put("UniBo", 2000);

        DTO uniMapDTO = DTOCreator.createDTO(DTOTypes.UNIVERSITY_MAP, map);
        assertNotNull(uniMapDTO);
        assertEquals(1000, uniMapDTO.getProperty("PoliMi"));
        assertEquals(2000, uniMapDTO.getProperty("UniBo"));
    }

    @Test
    void testDTOCreator_CreateRecommendationDTO() {
        Recommendation rec = new Recommendation();
        rec.setId(9999);
        rec.setStatus(RecommendationStatusEnum.acceptedMatch);

        Cv cv = new Cv();
        Student st = new Student();
        st.setId("stud-abc");
        st.setName("John Rec");
        cv.setStudent(st);
        rec.setCv(cv);

        InternshipOffer offer = new InternshipOffer();
        Company c = new Company();
        c.setId("comp-xyz");
        c.setName("RecCompany");
        offer.setCompany(c);
        offer.setId(2020);
        offer.setTitle("RecOfferTitle");
        rec.setInternshipOffer(offer);
        rec.setScore(0.8f);

        DTO recDTO = DTOCreator.createDTO(DTOTypes.RECOMMENDATION, rec);
        assertEquals(9999, recDTO.getProperty("id"));
        assertEquals("acceptedMatch", recDTO.getProperty("status"));
        assertEquals("stud-abc", recDTO.getProperty("studentID"));
        assertEquals("comp-xyz", recDTO.getProperty("companyID"));
        assertEquals(2020, recDTO.getProperty("internshipOfferID"));
        assertEquals(0.8f, recDTO.getProperty("score"));
    }

    @Test
    void testDTOCreator_CreateInternshipOfferDTO() {
        InternshipOffer offer = new InternshipOffer();
        offer.setId(3030);
        offer.setTitle("TestInternship");
        offer.setDescription("Desc...");
        offer.setDurationHours(120);
        offer.setLocation("Somewhere");
        offer.setNumberPositions(2);
        offer.setRequiredSkills("Java");
        offer.setStartDate(LocalDate.of(2023,1,1));
        offer.setEndDate(LocalDate.of(2023,6,1));
        offer.setCompensation(500);
        offer.setUpdateTime(Instant.now());

        Company comp = new Company();
        comp.setId("Comp-45");
        comp.setName("CompName");
        offer.setCompany(comp);

        DTO offerDTO = DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, offer);
        assertEquals("CompName", offerDTO.getProperty("companyName"));
        assertEquals("Comp-45", offerDTO.getProperty("companyID"));
        assertEquals(3030, offerDTO.getProperty("id"));
        assertEquals("TestInternship", offerDTO.getProperty("title"));
    }

    @Test
    void testDTOCreator_CreateInterviewTemplateDTO() {
        InterviewTemplate template = new InterviewTemplate();
        template.setId(999);
        template.setQuestion1("Q1");
        template.setQuestion2("Q2");
        template.setQuestion3("Q3");
        template.setQuestion4("Q4");
        template.setQuestion5("Q5");
        template.setQuestion6("Q6");

        DTO templateDTO = DTOCreator.createDTO(DTOTypes.INTERVIEW_TEMPLATE, template);
        assertNotNull(templateDTO);
        assertEquals(999, templateDTO.getProperty("id"));
        assertEquals("Q1", templateDTO.getProperty("question1"));
        assertEquals("Q6", templateDTO.getProperty("question6"));
    }

    @Test
    void testDTOCreator_CreateInternshipPosOfferDTO_withRecommendation() {
        InternshipPosOffer pos = new InternshipPosOffer();
        pos.setId(5001);
        pos.setStatus(InternshipPosOfferStatusEnum.pending);

        Interview interview = new Interview();
        Recommendation rec = new Recommendation();
        Cv cv = new Cv();
        Student s = new Student();
        s.setId("StudX");
        s.setName("StudNameX");
        cv.setStudent(s);

        InternshipOffer io = new InternshipOffer();
        io.setId(200);
        io.setTitle("PosOfferTitle");
        Company comp = new Company();
        comp.setId("CYYY");
        comp.setName("CompYYYY");
        io.setCompany(comp);
        rec.setCv(cv);
        rec.setInternshipOffer(io);

        interview.setRecommendation(rec);
        pos.setInterview(interview);

        DTO posDTO = DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, pos);
        assertEquals(5001, posDTO.getProperty("id"));
        assertEquals("pending", posDTO.getProperty("status"));
        assertEquals("CompYYYY", posDTO.getProperty("companyName"));
        assertEquals("StudNameX", posDTO.getProperty("studentName"));
    }

    @Test
    void testDTOCreator_CreateInternshipPosOfferDTO_withSpontaneousApplication() {
        InternshipPosOffer pos = new InternshipPosOffer();
        pos.setId(5002);
        pos.setStatus(InternshipPosOfferStatusEnum.pending);

        Interview interview = new Interview();
        SpontaneousApplication sa = new SpontaneousApplication();
        InternshipOffer io = new InternshipOffer();
        io.setId(201);
        io.setTitle("PosOfferTitle2");
        Company comp = new Company();
        comp.setId("CZZZ");
        comp.setName("CompZZZZ");
        io.setCompany(comp);
        sa.setInternshipOffer(io);

        Student s = new Student();
        s.setId("StudZ");
        s.setName("StudNameZ");
        sa.setStudent(s);

        interview.setSpontaneousApplication(sa);
        pos.setInterview(interview);

        DTO posDTO = DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, pos);
        assertEquals(5002, posDTO.getProperty("id"));
        assertEquals("CompZZZZ", posDTO.getProperty("companyName"));
        assertEquals("StudNameZ", posDTO.getProperty("studentName"));
    }

    @Test
    void testDTOCreator_CreateSpontaneousApplicationDTO() {
        SpontaneousApplication sa = new SpontaneousApplication();
        sa.setId(30303);
        sa.setStatus(SpontaneousApplicationStatusEnum.toBeEvaluated);

        InternshipOffer io = new InternshipOffer();
        io.setId(777);
        io.setTitle("SomeTitle");
        Company comp = new Company();
        comp.setName("CompANY");
        comp.setId("Comp777");
        io.setCompany(comp);
        sa.setInternshipOffer(io);

        Student st = new Student();
        st.setId("ST-99");
        st.setName("StudentName999");
        sa.setStudent(st);

        DTO saDTO = DTOCreator.createDTO(DTOTypes.SPONTANEOUS_APPLICATION, sa);
        assertEquals(30303, saDTO.getProperty("id"));
        assertEquals(SpontaneousApplicationStatusEnum.toBeEvaluated, saDTO.getProperty("status"));
        assertEquals("StudentName999", saDTO.getProperty("studentName"));
        assertEquals("CompANY", saDTO.getProperty("internshipOfferCompanyName"));
    }

    @Test
    void testDTOCreator_CreateInterviewQuizDTO() {
        InterviewQuiz quiz = new InterviewQuiz();
        quiz.setId(6000);
        quiz.setAnswer1("A1");
        quiz.setAnswer2("A2");
        quiz.setAnswer3("A3");
        quiz.setAnswer4("A4");
        quiz.setAnswer5("A5");
        quiz.setAnswer6("A6");
        quiz.setEvaluation(4);

        DTO quizDTO = DTOCreator.createDTO(DTOTypes.INTERVIEW_QUIZ, quiz);
        assertEquals(6000, quizDTO.getProperty("id"));
        assertEquals("A1", quizDTO.getProperty("answer1"));
        assertEquals(4, quizDTO.getProperty("evaluation"));
    }

    @Test
    void testDTOTypesEnumValues() {
        // Just to ensure coverage for the enum
        DTOTypes[] values = DTOTypes.values();
        assertTrue(values.length > 0);  // We have many entries
        DTOTypes cvType = DTOTypes.valueOf("CV");
        assertEquals(DTOTypes.CV, cvType);
    }
}
