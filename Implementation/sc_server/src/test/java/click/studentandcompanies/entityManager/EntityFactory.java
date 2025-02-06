package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Random;

/**
 * Abstract factory class for creating various entity instances.
 */
public abstract class EntityFactory {

    /**
     * Generates a random integer between the specified min and max values.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random integer between min and max
     */
    private String newRandomID(int min, int max) {
        return String.valueOf(new Random().nextInt((max - min) + 1) + min);
    }

    /**
     * Generates a random integer between the specified min and max values.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random integer between min and max
     */
    private Integer newRandom(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    /**
     * Generates a random float between the specified min and max values.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return a random float between min and max
     */
    private float newRandomFloat(float min, float max) {
        return min + new Random().nextFloat() * (max - min);
    }

    /**
     * Creates a new Student instance with the specified name, email, and university.
     *
     * @param name the name of the student
     * @param university the university the student is enrolled in
     * @return a new Student instance
     */
    protected Student setNewStudent(String name, University university) {
        Student student = new Student();
        student.setId(newRandomID(10001, 20000));
        student.setName(name);
        student.setEmail(name.toLowerCase() + "@example.edu");
        student.setUniversity(university);
        return student;
    }

    protected Student setNewStudent(int id, String name, University university) {
        Student student = new Student();
        student.setId(String.valueOf(id));
        student.setName(name);
        student.setEmail(name.toLowerCase() + "@example.edu");
        student.setUniversity(university);
        return student;
    }

    /**
     * Creates a new University instance with the specified name and country.
     *
     * @param name the name of the university
     * @param country the country where the university is located
     * @return a new University instance
     */
    protected University setNewUniversity(String name, String country) {
        University university = new University();
        university.setId(newRandomID(1, 10000));
        university.setName(name);
        university.setEmail(name.toLowerCase() + "@example.edu");
        university.setCountry(country);
        university.setVatNumber(newRandom(1000, 9999));
        return university;
    }

    /**
     * Creates a new University instance with the specified id, name and country.
     *
     * @param id the id of the university
     * @param name the name of the university
     * @param country the country where the university is located
     * @return a new University instance
     */
    protected University setNewUniversity(int id, String name, String country) {
        University university = new University();
        university.setId(String.valueOf(id));
        university.setName(name);
        university.setEmail(name.toLowerCase() + "@example.edu");
        university.setCountry(country);
        university.setVatNumber(newRandom(1000, 9999));
        return university;
    }

    /**
     * Creates a new Company instance with the specified name and country.
     *
     * @param name the name of the company
     * @param country the country where the company is located
     * @return a new Company instance
     */
    protected Company setNewCompany(String name, String country) {
        Company company = new Company();
        company.setId(newRandomID(20001, 30000));
        company.setName(name);
        company.setEmail(name.toLowerCase() + "@example.com");
        company.setCountry(country);
        company.setVatNumber(newRandom(10000, 99999));
        return company;
    }

    /**
     * Creates a new Company instance with the specified id, name and country.
     *
     * @param id the id of the company
     * @param name the name of the company
     * @param country the country where the company is located
     * @return a new Company instance
     */
    protected Company setNewCompany(int id, String name, String country) {
        Company company = new Company();
        company.setId(String.valueOf(id));
        company.setName(name);
        company.setEmail(name.toLowerCase() + "@example.com");
        company.setCountry(country);
        company.setVatNumber(newRandom(10000, 99999));
        return company;
    }

    /**
     * Creates a new InternshipOffer instance for the specified company.
     *
     * @param company the company offering the internship
     * @return a new InternshipOffer instance
     */
    protected InternshipOffer setNewInternshipOffer(Company company) {
        InternshipOffer internshipOffer = new InternshipOffer();
        internshipOffer.setId(newRandom(30001, 40000));
        internshipOffer.setTitle("Internship Offer Title " + internshipOffer.getId());
        internshipOffer.setDescription("Default description");
        internshipOffer.setRequiredSkills("Some skills");
        internshipOffer.setCompensation(newRandom(400, 2000));
        internshipOffer.setLocation("Random City");
        internshipOffer.setStartDate(LocalDate.now());
        internshipOffer.setEndDate(LocalDate.now().plusMonths(3));
        internshipOffer.setNumberPositions(newRandom(1, 5));
        internshipOffer.setDurationHours(newRandom(80, 480));
        internshipOffer.setCompany(company);
        return internshipOffer;
    }

    /**
     * Creates a new InternshipOffer instance for the specified id and company.
     *
     * @param id the id of the internship offer
     * @param company the company offering the internship
     * @return a new InternshipOffer instance
     */
    protected InternshipOffer setNewInternshipOffer(int id, Company company) {
        InternshipOffer internshipOffer = new InternshipOffer();
        internshipOffer.setId(id);
        internshipOffer.setTitle("Internship Offer Title " + internshipOffer.getId());
        internshipOffer.setDescription("Default description");
        internshipOffer.setRequiredSkills("Some skills");
        internshipOffer.setCompensation(newRandom(400, 2000));
        internshipOffer.setLocation("Random City");
        internshipOffer.setStartDate(LocalDate.now());
        internshipOffer.setEndDate(LocalDate.now().plusMonths(3));
        internshipOffer.setNumberPositions(newRandom(1, 5));
        internshipOffer.setDurationHours(newRandom(80, 480));
        internshipOffer.setCompany(company);
        return internshipOffer;
    }

    public Communication setNewCommunication(Student student, Company company) {
        Communication communication = new Communication();
        communication.setId(newRandom(40001, 50000));
        communication.setStudent(student);
        communication.setCompany(company);
        communication.setCommunicationType(CommunicationTypeEnum.communication);
        communication.setTitle("Communication Title " + communication.getId());
        communication.setContent("Default content");
        communication.setParticipantType(ParticipantTypeEnum.student);

        return communication;
    }


    public Communication setNewCommunication(int id, Student student, Company company) {
        Communication communication = new Communication();
        communication.setId(id);
        communication.setStudent(student);
        communication.setCompany(company);
        communication.setCommunicationType(CommunicationTypeEnum.communication);
        communication.setTitle("Communication Title " + communication.getId());
        communication.setContent("Default content");
        communication.setParticipantType(ParticipantTypeEnum.student);

        return communication;
    }

    public Message setNewMessage(int id, String body, String senderName, Communication communication) {
        Message message = new Message();
        message.setId(id);
        message.setBody(body);
        message.setSenderName(senderName);
        message.setCommunication(communication);
        return message;
    }

//    /**
//     * Creates a new Account instance with the specified name and email.
//     *
//     * @param name the name of the account holder
//     * @param email the email of the account holder
//     * @return a new Account instance
//     */
//    protected Account setNewAccount(String name, String email) {
//        Account account = new Account();
//        account.setUserID("UUID-" + newRandom(1, 999999));
//        account.setName(name);
//        account.setEmail(email);
//        account.setEnrolledInUniId(newRandom(1, 9999));
//        account.setVatNumber(newRandom(1000, 999999));
//        account.setCountry("IT");
//        account.setValidate(newRandomBoolean());
//        return account;
//    }
//
//    /**
//     * Creates a new Account instance with the specified id, name and email.
//     *
//     * @param id the id of the account
//     * @param name the name of the account holder
//     * @param email the email of the account holder
//     * @return a new Account instance
//     */
//    protected Account setNewAccount(int id, String name, String email) {
//        Account account = new Account();
//        account.setUserID("UUID-" + newRandom(1, 999999));
//        account.setName(name);
//        account.setEmail(email);
//        account.setEnrolledInUniId(newRandom(1, 9999));
//        account.setVatNumber(newRandom(1000, 999999));
//        account.setCountry("IT");
//        account.setValidate(newRandomBoolean());
//        return account;
//    }

    /**
     * Creates a new Cv instance for the specified student.
     *
     * @param student the student to whom the CV belongs
     * @return a new Cv instance
     */
    protected Cv setNewCv(Student student) {
        Cv cv = new Cv();
        cv.setId(newRandom(50001, 60000));
        cv.setStudent(student);
        cv.setSkills("Java, Spring");
        cv.setWorkExperiences("Intern at MyCompany");
        cv.setEducation("Bachelor in Computer Science");
        cv.setProject("Sample Project");
        cv.setCertifications("None");
        cv.setUpdateTime(Instant.now());
        return cv;
    }

    /**
     * Creates a new Cv instance for the specified id and student.
     *
     * @param id the id of the CV
     * @param student the student to whom the CV belongs
     * @return a new Cv instance
     */
    protected Cv setNewCv(int id, Student student) {
        Cv cv = new Cv();
        cv.setId(id);
        cv.setStudent(student);
        cv.setSkills("Java, Spring");
        cv.setWorkExperiences("Intern at MyCompany");
        cv.setEducation("Bachelor in Computer Science");
        cv.setProject("Sample Project");
        cv.setCertifications("None");
        cv.setUpdateTime(Instant.now());
        return cv;
    }

    /**
     * Creates a new Feedback instance for the specified recommendation and participant type.
     *
     * @param recommendation the recommendation related to the feedback
     * @param participantType the type of participant providing the feedback
     * @return a new Feedback instance
     */
    protected Feedback setNewFeedback(Recommendation recommendation, ParticipantTypeEnum participantType) {
        Feedback feedback = new Feedback();
        feedback.setId(newRandom(60001, 70000));
        feedback.setRecommendation(recommendation);
        feedback.setParticipantType(participantType);
        feedback.setRating(newRandom(1, 5));
        //feedback.setComment("Default comment");
        feedback.setUploadTime(Instant.now());

        if (participantType == ParticipantTypeEnum.student) {
            feedback.setStudent(recommendation.getCv().getStudent());
        } else {
            feedback.setCompany(recommendation.getInternshipOffer().getCompany());
        }
        return feedback;
    }

    /**
     * Creates a new InternshipPosOffer instance.
     *
     * @return a new InternshipPosOffer instance
     */
    protected InternshipPosOffer setNewInternshipPosOffer() {
        InternshipPosOffer ipo = new InternshipPosOffer();
        ipo.setId(newRandom(70001, 80000));
        ipo.setStatus(InternshipPosOfferStatusEnum.pending);
        return ipo;
    }

    /**
     * Creates a new Interview instance with the specified parameters.
     *
     * @param interviewTemplate the interview template used for the interview
     * @param recommendation the recommendation related to the interview
     * @param spontaneousApplication the spontaneous application related to the interview
     * @param ipo the internship position offer related to the interview
     * @return a new Interview instance
     */
    /*protected Interview setNewInterview(InterviewTemplate interviewTemplate, Recommendation recommendation,
                                        SpontaneousApplication spontaneousApplication, InternshipPosOffer ipo) {
        Interview interview = new Interview();
        interview.setId(newRandom(80001, 90000));
        interview.setInterviewTemplate(interviewTemplate);
        interview.setRecommendation(recommendation);
        interview.setSpontaneousApplication(spontaneousApplication);
        interview.setInternshipPosOffer(ipo);
        interview.setStatus(InterviewStatusEnum.toBeSubmitted);
        interview.setAnswer("No answer yet");
        interview.setEvaluation(null);
        return interview;
    }*/

    /**
     * Creates a new InterviewTemplate instance for the specified company.
     *
     * @param company the company creating the interview template
     * @return a new InterviewTemplate instance
     */
    /*protected InterviewTemplate setNewInterviewTemplate(Company company) {
        InterviewTemplate template = new InterviewTemplate();
        template.setId(newRandom(90001, 100000));
        template.setCompany(company);
        template.setQuestions("1) Tell us about yourself?\n2) Why do you want this job?");
        return template;
    }*/

    /**
     * Creates a new Recommendation instance for the specified internship offer and CV.
     *
     * @param internshipOffer the internship offer related to the recommendation
     * @param cv the CV related to the recommendation
     * @return a new Recommendation instance
     */
    protected Recommendation setNewRecommendation(InternshipOffer internshipOffer, Cv cv) {
        Recommendation recommendation = new Recommendation();
        recommendation.setId(newRandom(100001, 110000));
        recommendation.setInternshipOffer(internshipOffer);
        recommendation.setCv(cv);
        recommendation.setStatus(RecommendationStatusEnum.acceptedMatch);
        recommendation.setScore(newRandomFloat(0.0f, 1.0f));
        return recommendation;
    }

    /**
     * Creates a new SpontaneousApplication instance for the specified student and internship offer.
     *
     * @param student the student applying spontaneously
     * @param internshipOffer the internship offer the student is applying for
     * @return a new SpontaneousApplication instance
     */
    protected SpontaneousApplication setNewSpontaneousApplication(Student student, InternshipOffer internshipOffer) {
        SpontaneousApplication application = new SpontaneousApplication();
        application.setId(newRandom(110001, 120000));
        application.setStudent(student);
        application.setInternshipOffer(internshipOffer);
        application.setStatus(SpontaneousApplicationStatusEnum.toBeEvaluated);
        return application;
    }
}