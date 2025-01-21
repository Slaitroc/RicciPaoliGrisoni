package click.studentandcompanies.entityManager.feedbackMechanism.feedbackMechanismCommands;

import click.studentandcompanies.entity.Feedback;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.FeedbackRepository;
import click.studentandcompanies.entityManager.feedbackMechanism.FeedbackMechanismCommand;

import java.util.List;
import java.util.Map;

public class SubmitFeedbackCommand implements FeedbackMechanismCommand<Feedback> {
    private final FeedbackRepository feedbackRepository;
    private final UserManager userManager;
    private final Map<String, Object> payload;
    private final Integer recommendationID;
    public SubmitFeedbackCommand(Integer recommendationID, Map<String, Object> payload, FeedbackRepository feedbackRepository, UserManager userManager) {
        this.recommendationID = recommendationID;
        this.payload = payload;
        this.userManager = userManager;
        this.feedbackRepository = feedbackRepository;
    }
    @Override
    public Feedback execute() throws IllegalArgumentException{
        validateFeedbackPayload(payload);
        Integer submitter_id = (Integer) payload.get("student_id") != null ? (Integer) payload.get("student_id") : (Integer) payload.get("company_id");
        ParticipantTypeEnum participantType = userManager.getParticipantType(submitter_id);
        if(participantType == null || participantType != ParticipantTypeEnum.valueOf((String) payload.get("participant_type"))){
            throw new IllegalArgumentException("Participant_type is not valid");
        }
        List<Recommendation> submitterRecommendations;
        if(participantType == ParticipantTypeEnum.student) {
            submitterRecommendations = userManager.getRecommendationByStudentId(submitter_id);
        }else{
            submitterRecommendations = userManager.getRecommendationByCompanyId(submitter_id);
        }
        if(submitterRecommendations.stream().noneMatch(recommendation -> recommendation.getId().equals(recommendationID))){
            throw new IllegalArgumentException("Recommendation_id is not valid");
        }
        Recommendation recommendation = userManager.getRecommendationById(recommendationID);
        if(recommendation.getStatus() != RecommendationStatusEnum.acceptedMatch){
            throw new IllegalArgumentException("Feedback can only be submitted for accepted matches");
        }
        Feedback feedback = createFeedback(recommendationID,submitter_id,participantType,payload);
        feedbackRepository.save(feedback);
        return feedback;
    }

    private void validateFeedbackPayload(Map<String, Object> payload){
        if(payload.get("student_id")==null && payload.get("company_id")==null){
            throw new IllegalArgumentException("Student_id or company_id is required");
        }
        if(payload.get("student_id")!=null && payload.get("company_id")!=null){
            throw new IllegalArgumentException("Student_id and company_id can't be both present");
        }
        if(payload.get("participant_type")==null){
            throw new IllegalArgumentException("Participant_type is required");
        }
        if(payload.get("rating")==null){
            throw new IllegalArgumentException("Rating is required");
        }
        if((Integer) payload.get("rating") < 1 || (Integer) payload.get("rating") > 5){
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }

    private Feedback createFeedback(Integer recommendationID,Integer submitterID ,ParticipantTypeEnum participantType,Map<String, Object> payload){
        Integer rating = (Integer) payload.get("rating");
        String comment = (String) payload.get("comment") != null ? (String) payload.get("comment") : "";
        Feedback feedback = new Feedback(userManager.getRecommendationById(recommendationID), participantType, rating, comment);
        if(participantType == ParticipantTypeEnum.student){
            feedback.setStudent(userManager.getStudentById(submitterID));
        }else{
            feedback.setCompany(userManager.getCompanyById(submitterID));
        }
        return feedback;
    }

}