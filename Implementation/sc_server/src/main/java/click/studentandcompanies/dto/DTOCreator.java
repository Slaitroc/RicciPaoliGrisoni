package click.studentandcompanies.dto;

import click.studentandcompanies.entity.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DTOCreator {
    public static DTO createDTO(DTOTypes dtoType, Object obj) {
        return functionMap.get(dtoType).apply(obj);
    }

    private static final Map<DTOTypes, Function<Object, DTO>> functionMap = new HashMap<>();
    static {
        functionMap.put(DTOTypes.ERROR, obj -> createErrorDTO((String) obj));
        functionMap.put(DTOTypes.EMPTY, obj -> createEmptyDTO((String) obj));
        functionMap.put(DTOTypes.STUDENT, obj -> createStudentDTO((Student) obj));
        functionMap.put(DTOTypes.STUDENT_V2, obj -> createStudentDTOV2((Student) obj));
        functionMap.put(DTOTypes.UNIVERSITY, obj -> createUniversityDTO((University) obj));
        functionMap.put(DTOTypes.RECOMMENDATION_UPDATED_STATUS, object -> createRecommendationUpdatedStatusDTO((Recommendation) object));
        functionMap.put(DTOTypes.INTERNSHIP_OFFER, object -> createInternshipOfferDTO((InternshipOffer) object));
        functionMap.put(DTOTypes.CV, object -> createCVDTO((Cv) object));
        functionMap.put(DTOTypes.SPONTANEOUS_APPLICATION, object -> createSpontaneousApplicationDTO((SpontaneousApplication) object));
        functionMap.put(DTOTypes.FEEDBACK, object -> createFeedbackDTO((Feedback) object));
        functionMap.put(DTOTypes.RECOMMENDATION, object -> createRecommendationDTO((Recommendation) object));
        functionMap.put(DTOTypes.COMMUNICATION, object -> createCommunicationDTO((Communication) object));
        functionMap.put(DTOTypes.INTERVIEW, object -> createInterviewDTO((Interview) object));
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

    private static DTO createEmptyDTO(String message) {
        final DTO errorDTO = new DTO();
        errorDTO.addProperty("empty", message);
        return errorDTO;
    }

    private static DTO createStudentDTO(Student student) {
        final DTO studentDTO = new DTO();
        studentDTO.addProperty("id", student.getId());
        studentDTO.addProperty("name", student.getName());
        studentDTO.addProperty("email", student.getEmail());
        return studentDTO;
    }

    private static DTO createStudentDTOV2(Student student) {
        final DTO studentDTO = new DTO();
        studentDTO.addProperty("id", student.getId());
        studentDTO.addProperty("name", student.getName());
        studentDTO.addProperty("email", student.getEmail());
        return studentDTO;
    }

    private static DTO createUniversityDTO(University university) {
        final DTO universityDTO = new DTO();
        universityDTO.addProperty("id", university.getId());
        universityDTO.addProperty("name", university.getName());
        universityDTO.addProperty("email", university.getEmail());
        universityDTO.addProperty("country", university.getCountry());
        universityDTO.addProperty("vatNumber", university.getVatNumber());
        return universityDTO;
    }

    private static DTO createInternshipOfferDTO(InternshipOffer offer) {
        final DTO offerDTO = new DTO();
        offerDTO.addProperty("id", offer.getId());
        offerDTO.addProperty("compensation", offer.getCompensation());
        offerDTO.addProperty("description", offer.getDescription());
        offerDTO.addProperty("duration", offer.getDurationHours());
        offerDTO.addProperty("company", offer.getCompany());
        offerDTO.addProperty("endDate", offer.getEndDate());
        offerDTO.addProperty("location", offer.getLocation());
        offerDTO.addProperty("numberPositions", offer.getNumberPositions());
        offerDTO.addProperty("requiredSkills", offer.getRequiredSkills());
        offerDTO.addProperty("startDate", offer.getStartDate());
        offerDTO.addProperty("title", offer.getTitle());
        offerDTO.addProperty("company", offer.getCompany());
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
        cvDTO.addProperty("work_experience", cv.getWorkExperiences());
        cvDTO.addProperty("student_id", cv.getStudent().getId());
        return cvDTO;
    }

    private static DTO createSpontaneousApplicationDTO(SpontaneousApplication application){
        final DTO appDTO = new DTO();
        appDTO.addProperty("id", application.getId());
        appDTO.addProperty("status", application.getStatus());
        appDTO.addProperty("internship_offer_title", application.getInternshipOffer().getTitle());
        appDTO.addProperty("internship_offer_company_name", application.getInternshipOffer().getCompany().getName());
        appDTO.addProperty("student", application.getStudent().getName());
        return appDTO;
    }

    private static DTO createFeedbackDTO(Feedback feedback){
        final DTO feedbackDTO = new DTO();
        feedbackDTO.addProperty("id", feedback.getId());
        if(feedback.getComment()!=null){
            feedbackDTO.addProperty("comment", feedback.getComment());
        }
        feedbackDTO.addProperty("participant_type", feedback.getParticipantType());
        feedbackDTO.addProperty("rating", feedback.getRating());
        if(feedback.getStudent()!=null){
            feedbackDTO.addProperty("student_id", feedback.getStudent().getId());
        }
        if(feedback.getCompany()!=null) {
            feedbackDTO.addProperty("company_id", feedback.getCompany().getId());
        }
        feedbackDTO.addProperty("recommendation_id", feedback.getRecommendation().getId());
        return feedbackDTO;
    }

    private static DTO createRecommendationDTO(Recommendation recommendation) {
        final DTO recommendationDTO = new DTO();
        recommendationDTO.addProperty("id", recommendation.getId());
        recommendationDTO.addProperty("status", recommendation.getStatus().toString());
        recommendationDTO.addProperty("student_name", recommendation.getCv().getStudent().getName());
        recommendationDTO.addProperty("company_name", recommendation.getInternshipOffer().getCompany().getName());
        recommendationDTO.addProperty("internship_offer_title", recommendation.getInternshipOffer().getTitle());
        recommendationDTO.addProperty("student_id", recommendation.getCv().getStudent().getId());
        recommendationDTO.addProperty("company_id", recommendation.getInternshipOffer().getCompany().getId());
        recommendationDTO.addProperty("internship_offer_id", recommendation.getInternshipOffer().getId());
        return recommendationDTO;
    }

    private static DTO createCommunicationDTO(Communication communication) {
        final DTO communicationDTO = new DTO();
        communicationDTO.addProperty("id", communication.getId());
        communicationDTO.addProperty("type", communication.getCommunicationType());
        communicationDTO.addProperty("title", communication.getTitle());
        communicationDTO.addProperty("content", communication.getContent());
        communicationDTO.addProperty("internshipOffer_id", communication.getInternshipOffer().getId());
        communicationDTO.addProperty("internshipOffer_title", communication.getInternshipOffer().getTitle());
        communicationDTO.addProperty("company", communication.getInternshipOffer().getCompany().getName());
        communicationDTO.addProperty("student", communication.getStudent().getName());
        communicationDTO.addProperty("university", communication.getUniversity().getName());

        return communicationDTO;
    }

    private static DTO createInterviewDTO(Interview interview){
        final DTO interviewDTO = new DTO();
        interviewDTO.addProperty("id", interview.getId());
        interviewDTO.addProperty("status", interview.getStatus().toString());
        return interviewDTO;
    }
}
