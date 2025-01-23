package click.studentandcompanies.entityManager.feedbackMechanism.feedbackMechanismCommands;

import click.studentandcompanies.entity.Feedback;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.FeedbackRepository;
import click.studentandcompanies.entityManager.feedbackMechanism.FeedbackMechanismCommand;
import click.studentandcompanies.utils.exception.BadInputException;

import java.time.Instant;
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
    public Feedback execute() throws BadInputException{
        validateFeedbackPayload(payload);
        Integer submitter_id = (Integer) payload.get("student_id") != null ? (Integer) payload.get("student_id") : (Integer) payload.get("company_id");
        ParticipantTypeEnum participantType = userManager.getParticipantType(submitter_id);
        if(participantType == null || participantType != ParticipantTypeEnum.valueOf((String) payload.get("participant_type"))){
            throw new BadInputException("Participant_type is not valid");
        }
        List<Recommendation> submitterRecommendations;
        if(participantType == ParticipantTypeEnum.student) {
            submitterRecommendations = userManager.getRecommendationByStudentId(submitter_id);
        }else{
            submitterRecommendations = userManager.getRecommendationByCompanyId(submitter_id);
        }
        if(submitterRecommendations.stream().noneMatch(recommendation -> recommendation.getId().equals(recommendationID))){
            throw new BadInputException("Recommendation_id is not valid");
        }
        Recommendation recommendation = userManager.getRecommendationById(recommendationID);
        if(recommendation.getStatus() != RecommendationStatusEnum.acceptedMatch){
            throw new BadInputException("Feedback can only be submitted for accepted matches");
        }
        if(participantType == ParticipantTypeEnum.student && !recommendation.getCv().getStudent().getId().equals(submitter_id)){
            throw new BadInputException("Student can only submit feedback for his own recommendation");
        }
        if(participantType == ParticipantTypeEnum.company && !recommendation.getInternshipOffer().getCompany().getId().equals(submitter_id)){
            throw new BadInputException("Company can only submit feedback for his own recommendation");
        }
        Feedback feedback = createFeedback(recommendationID,submitter_id,participantType,payload);
        feedbackRepository.save(feedback);
        return feedback;
    }

    private void validateFeedbackPayload(Map<String, Object> payload){
        if(payload.get("student_id")==null && payload.get("company_id")==null){
            throw new BadInputException("Student_id or company_id is required");
        }
        if(payload.get("student_id")!=null && payload.get("company_id")!=null){
            throw new BadInputException("Student_id and company_id can't be both present");
        }
        if(payload.get("participant_type")==null){
            throw new BadInputException("Participant_type is required");
        }
        if(payload.get("rating")==null){
            throw new BadInputException("Rating is required");
        }
        if((Integer) payload.get("rating") < 1 || (Integer) payload.get("rating") > 5){
            throw new BadInputException("Rating must be between 1 and 5");
        }
        if(payload.get("upload_time")==null){
            throw new BadInputException("Upload time is required");
        }
        Instant uploadTime = Instant.parse(String.valueOf(payload.get("upload_time")));
        if(uploadTime.isAfter(Instant.now())){
            throw new BadInputException("Upload time can't be in the future");
        }
    }

    private Feedback createFeedback(Integer recommendationID,Integer submitterID ,ParticipantTypeEnum participantType,Map<String, Object> payload){
        Integer rating = (Integer) payload.get("rating");
        String comment = (String) payload.get("comment") != null ? (String) payload.get("comment") : "";
        Instant uploadTime = Instant.parse(String.valueOf(payload.get("upload_time")));
        Feedback feedback = new Feedback(userManager.getRecommendationById(recommendationID), participantType, rating, comment, uploadTime);
        if(participantType == ParticipantTypeEnum.student){
            feedback.setStudent(userManager.getStudentById(submitterID));
        }else{
            feedback.setCompany(userManager.getCompanyById(submitterID));
        }
        return feedback;
    }

}