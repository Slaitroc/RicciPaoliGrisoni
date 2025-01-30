package click.studentandcompanies.dto;

import click.studentandcompanies.entity.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DTOCreator {
    public static DTO createDTO(DTOTypes dtoType, Object obj) {
        return functionMap.get(dtoType).apply(obj);
    }
    //todo: ask Sam: forse é meglio usare i nomi completi delle proprietá
    // ad esempio company_title o internship_name, company_id invece di (company, internship, name, title, id ...)
    // aiuterebbe il frontend e anche nelle notifiche ho notato puó essere utile
    //(sam) okie dokie
    private static final Map<DTOTypes, Function<Object, DTO>> functionMap = new HashMap<>();
    static {
        functionMap.put(DTOTypes.ERROR, obj -> createErrorDTO((String) obj));
        functionMap.put(DTOTypes.STUDENT, obj -> createStudentDTO((Student) obj));
        //functionMap.put(DTOTypes.STUDENT_V2, obj -> createStudentDTOV2((Student) obj));
        functionMap.put(DTOTypes.UNIVERSITY, obj -> createUniversityDTO((University) obj));
        functionMap.put(DTOTypes.RECOMMENDATION_UPDATED_STATUS, object -> createRecommendationUpdatedStatusDTO((Recommendation) object));
        functionMap.put(DTOTypes.INTERNSHIP_OFFER, object -> createInternshipOfferDTO((InternshipOffer) object));
        functionMap.put(DTOTypes.CV, object -> createCVDTO((Cv) object));
        functionMap.put(DTOTypes.SPONTANEOUS_APPLICATION, object -> createSpontaneousApplicationDTO((SpontaneousApplication) object));
        functionMap.put(DTOTypes.FEEDBACK, object -> createFeedbackDTO((Feedback) object));
        functionMap.put(DTOTypes.RECOMMENDATION, object -> createRecommendationDTO((Recommendation) object));
        functionMap.put(DTOTypes.COMMUNICATION, object -> createCommunicationDTO((Communication) object));
        functionMap.put(DTOTypes.INTERVIEW, object -> createInterviewDTO((Interview) object));
        functionMap.put(DTOTypes.INTERVIEW_TEMPLATE, object -> createInterviewTemplateDTO((InterviewTemplate) object));
        functionMap.put(DTOTypes.INTERNSHIP_POS_OFFER, object -> createInternshipPosOfferDTO((InternshipPosOffer) object));
        functionMap.put(DTOTypes.ACCOUNT, object -> createAccountDTO((Account) object));
        functionMap.put(DTOTypes.UNIVERSITY_MAP, object -> createUniversityMapDTO((Map<String, Integer>) object));
    }

    private static DTO createUniversityMapDTO(Map<String, Integer> universities) {
        final DTO universityMapDTO = new DTO();
        universities.forEach(universityMapDTO::addProperty);
        return universityMapDTO;
    }
    private static DTO createAccountDTO(Account account) {
        final DTO accountDTO = new DTO();
        accountDTO.addProperty("userID", account.getUserID());
        accountDTO.addProperty("userType", account.getUserType());
        accountDTO.addProperty("name", account.getName());
        accountDTO.addProperty("email", account.getEmail());
        accountDTO.addProperty("uniVat", account.getUniVat());
        accountDTO.addProperty("vatNumber", account.getVatNumber());
        accountDTO.addProperty("country", account.getCountry());
        accountDTO.addProperty("location", account.getLocation());
        accountDTO.addProperty("validate", account.getValidate());
        accountDTO.addProperty("uniDesc", account.getUniDesc());
        accountDTO.addProperty("birthDate", account.getBirthDate());
        return accountDTO;
    }

    private static DTO createInternshipPosOfferDTO(InternshipPosOffer internshipPosOffer){
        final DTO internshipPosOfferDTO = new DTO();
        internshipPosOfferDTO.addProperty("id", internshipPosOffer.getId());
        internshipPosOfferDTO.addProperty("status", internshipPosOffer.getStatus().toString());
        return internshipPosOfferDTO;
    }
    private static DTO createInterviewTemplateDTO(InterviewTemplate interviewTemplate){
        final DTO interviewTemplateDTO = new DTO();
        interviewTemplateDTO.addProperty("id", interviewTemplate.getId());
        interviewTemplateDTO.addProperty("questions", interviewTemplate.getQuestions());
        interviewTemplateDTO.addProperty("company", interviewTemplate.getCompany().getName());
        return interviewTemplateDTO;
    }

    private static DTO createRecommendationUpdatedStatusDTO(Recommendation recommendation) {
        final DTO recommendationDTO = new DTO();
        recommendationDTO.addProperty("id", recommendation.getId());
        recommendationDTO.addProperty("status", recommendation.getStatus().toString());
        return recommendationDTO;
    }

    private static DTO createErrorDTO(String message) {
        final DTO errorDTO = new DTO();
        errorDTO.addProperty("error", message);
        return errorDTO;
    }

    private static DTO createStudentDTO(Student student) {
        final DTO studentDTO = new DTO();
        studentDTO.addProperty("id", student.getId());
        studentDTO.addProperty("name", student.getName());
        studentDTO.addProperty("surname", student.getSurname());
        studentDTO.addProperty("email", student.getEmail());
        studentDTO.addProperty("birthDate", student.getBirthdate());
        studentDTO.addProperty("country", student.getCountry());
        studentDTO.addProperty("universityID", student.getUniversity().getId());
        studentDTO.addProperty("universityName", student.getUniversity().getName());
        return studentDTO;
    }

    /*private static DTO createStudentDTOV2(Student student) {
        final DTO studentDTO = new DTO();
        studentDTO.addProperty("id", student.getId());
        studentDTO.addProperty("name", student.getName());
        studentDTO.addProperty("email", student.getEmail());
        return studentDTO;
    }*/

    private static DTO createUniversityDTO(University university) {
        final DTO universityDTO = new DTO();
        universityDTO.addProperty("id", university.getId());
        universityDTO.addProperty("name", university.getName());
        universityDTO.addProperty("email", university.getEmail());
        universityDTO.addProperty("country", university.getCountry());
        universityDTO.addProperty("vatNumber", university.getVatNumber());
        universityDTO.addProperty("uniDesc", university.getUniDesc());
        universityDTO.addProperty("location", university.getLocation());
        return universityDTO;
    }

    private static DTO createInternshipOfferDTO(InternshipOffer offer) {
        final DTO offerDTO = new DTO();
        offerDTO.addProperty("id", offer.getId());
        offerDTO.addProperty("compensation", offer.getCompensation());
        offerDTO.addProperty("description", offer.getDescription());
        offerDTO.addProperty("duration", offer.getDurationHours());
        offerDTO.addProperty("companyID", offer.getCompany().getId());
        offerDTO.addProperty("companyName", offer.getCompany().getName());
        offerDTO.addProperty("endDate", offer.getEndDate());
        offerDTO.addProperty("location", offer.getLocation());
        offerDTO.addProperty("numberPositions", offer.getNumberPositions());
        offerDTO.addProperty("requiredSkills", offer.getRequiredSkills());
        offerDTO.addProperty("startDate", offer.getStartDate());
        offerDTO.addProperty("title", offer.getTitle());
        return offerDTO;
    }

    private static DTO createCVDTO(Cv cv) {
        final DTO cvDTO = new DTO();
        cvDTO.addProperty("id", cv.getId());
        cvDTO.addProperty("certifications", cv.getCertifications());
        cvDTO.addProperty("education", cv.getEducation());
        cvDTO.addProperty("project", cv.getProject());
        cvDTO.addProperty("skills", cv.getSkills());
        cvDTO.addProperty("update_time", cv.getUpdateTime());
        cvDTO.addProperty("workExperience", cv.getWorkExperiences());
        cvDTO.addProperty("studentID", cv.getStudent().getId());
        cvDTO.addProperty("student_name", cv.getStudent().getName());
        return cvDTO;
    }

    private static DTO createSpontaneousApplicationDTO(SpontaneousApplication application){
        final DTO appDTO = new DTO();
        appDTO.addProperty("id", application.getId());
        appDTO.addProperty("status", application.getStatus());
        appDTO.addProperty("internshipOfferTitle", application.getInternshipOffer().getTitle());
        appDTO.addProperty("internshipOfferCompanyName", application.getInternshipOffer().getCompany().getName());
        appDTO.addProperty("studentName", application.getStudent().getName());
        appDTO.addProperty("studentID", application.getStudent().getId());
        return appDTO;
    }

    private static DTO createFeedbackDTO(Feedback feedback){
        final DTO feedbackDTO = new DTO();
        feedbackDTO.addProperty("id", feedback.getId());
        if(feedback.getComment()!=null){
            feedbackDTO.addProperty("comment", feedback.getComment());
        }
        feedbackDTO.addProperty("participantType", feedback.getParticipantType());
        feedbackDTO.addProperty("rating", feedback.getRating());
        if(feedback.getStudent()!=null){
            feedbackDTO.addProperty("studentID", feedback.getStudent().getId());
        }
        if(feedback.getCompany()!=null) {
            feedbackDTO.addProperty("companyID", feedback.getCompany().getId());
        }
        feedbackDTO.addProperty("recommendationID", feedback.getRecommendation().getId());
        return feedbackDTO;
    }

    private static DTO createRecommendationDTO(Recommendation recommendation) {
        final DTO recommendationDTO = new DTO();
        recommendationDTO.addProperty("id", recommendation.getId());
        recommendationDTO.addProperty("status", recommendation.getStatus().toString());
        recommendationDTO.addProperty("studentName", recommendation.getCv().getStudent().getName());
        recommendationDTO.addProperty("companyName", recommendation.getInternshipOffer().getCompany().getName());
        recommendationDTO.addProperty("internshipOfferTitle", recommendation.getInternshipOffer().getTitle());
        recommendationDTO.addProperty("studentID", recommendation.getCv().getStudent().getId());
        recommendationDTO.addProperty("companyID", recommendation.getInternshipOffer().getCompany().getId());
        recommendationDTO.addProperty("internshipOfferID", recommendation.getInternshipOffer().getId());
        recommendationDTO.addProperty("score", recommendation.getScore());
        return recommendationDTO;
    }

    private static DTO createCommunicationDTO(Communication communication) {
        final DTO communicationDTO = new DTO();
        communicationDTO.addProperty("id", communication.getId());
        communicationDTO.addProperty("type", communication.getCommunicationType());
        communicationDTO.addProperty("title", communication.getTitle());
        communicationDTO.addProperty("content", communication.getContent());
        communicationDTO.addProperty("internshipOfferID", communication.getInternshipOffer().getId());
        communicationDTO.addProperty("internshipOfferTitle", communication.getInternshipOffer().getTitle());
        communicationDTO.addProperty("companyName", communication.getInternshipOffer().getCompany().getName());
        communicationDTO.addProperty("studentName", communication.getStudent().getName());
        communicationDTO.addProperty("universityName", communication.getUniversity().getName());

        return communicationDTO;
    }

    private static DTO createInterviewDTO(Interview interview){
        final DTO interviewDTO = new DTO();
        interviewDTO.addProperty("id", interview.getId());
        interviewDTO.addProperty("status", interview.getStatus().toString());
        if(interview.getSpontaneousApplication() == null) {
            interviewDTO.addProperty("internshipTitle", interview.getRecommendation().getInternshipOffer().getTitle());
            interviewDTO.addProperty("companyName", interview.getRecommendation().getInternshipOffer().getCompany().getName());
            interviewDTO.addProperty("companyID", interview.getRecommendation().getInternshipOffer().getCompany().getId());
            interviewDTO.addProperty("studentName", interview.getRecommendation().getCv().getStudent().getName());
            interviewDTO.addProperty("studentID", interview.getRecommendation().getCv().getStudent().getId());
        }
        else {
            interviewDTO.addProperty("internshipTitle", interview.getSpontaneousApplication().getInternshipOffer().getCompany().getName());
            interviewDTO.addProperty("companyName", interview.getSpontaneousApplication().getInternshipOffer().getCompany().getName());
            interviewDTO.addProperty("companyID", interview.getSpontaneousApplication().getInternshipOffer().getCompany().getId());
            interviewDTO.addProperty("studentName", interview.getSpontaneousApplication().getStudent().getName());
            interviewDTO.addProperty("studentID", interview.getSpontaneousApplication().getStudent().getId());
        }
        return interviewDTO;
    }
}
