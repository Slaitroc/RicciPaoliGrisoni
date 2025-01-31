package click.studentandcompanies.entityManager.feedbackMechanism.feedbackMechanismCommands;

import click.studentandcompanies.entity.Feedback;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.FeedbackRepository;
import click.studentandcompanies.entityManager.feedbackMechanism.FeedbackMechanismCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.WrongStateException;

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

        String user_id = (String) payload.get("user_id");
        ParticipantTypeEnum participantType = userManager.getParticipantType(user_id);
        Recommendation recommendation = userManager.getRecommendationById(recommendationID);

        validateRecommendation(recommendation);
        validateParticipant(participantType, user_id,recommendation);
        validateSubmitterRecommendations(user_id,participantType);

        Feedback feedback = createFeedback(recommendationID,user_id,participantType,payload);
        feedbackRepository.save(feedback);
        return feedback;
    }

    private void validateFeedbackPayload(Map<String, Object> payload){
        if(payload.get("user_id")==null){
            throw new BadInputException("User_id is required");
        }
        if(payload.get("rating")==null){
            throw new BadInputException("Rating is required");
        }
        if((Integer) payload.get("rating") < 1 || (Integer) payload.get("rating") > 5){
            throw new BadInputException("Rating must be between 1 and 5");
        }
    }



    private void validateParticipant(ParticipantTypeEnum participantType, String submitter_id, Recommendation recommendation){
        if(participantType == ParticipantTypeEnum.student && !recommendation.getCv().getStudent().getId().equals(submitter_id)){
            throw new BadInputException("Student can only submit feedback for his own recommendation");
        }
        if(participantType == ParticipantTypeEnum.company && !recommendation.getInternshipOffer().getCompany().getId().equals(submitter_id)){
            throw new BadInputException("Company can only submit feedback for his own recommendation");
        }
        if(participantType == null){
            throw new BadInputException("Invalid user type");
        }
    }

    private void validateSubmitterRecommendations(String submitter_id, ParticipantTypeEnum participantType){
        List<Recommendation> submitterRecommendations;
        if(participantType == ParticipantTypeEnum.student) {
            submitterRecommendations = userManager.getRecommendationByStudentId(submitter_id);
        }else{
            submitterRecommendations = userManager.getRecommendationByCompanyId(submitter_id);
        }
        if(submitterRecommendations.stream().noneMatch(r -> r.getId().equals(recommendationID))){
            throw new BadInputException("Participant can only submit feedback for his own recommendation");
        }
    }

    private Feedback createFeedback(Integer recommendationID,String submitterID ,ParticipantTypeEnum participantType,Map<String, Object> payload){
        Integer rating = (Integer) payload.get("rating");
        Feedback feedback = new Feedback(userManager.getRecommendationById(recommendationID), participantType, rating, Instant.now());
        if(participantType == ParticipantTypeEnum.student){
            feedback.setStudent(userManager.getStudentById(submitterID));
        }else{
            feedback.setCompany(userManager.getCompanyById(submitterID));
        }
        return feedback;
    }

    private void validateRecommendation(Recommendation recommendation){
        if(recommendation.getStatus() != RecommendationStatusEnum.acceptedMatch){
            throw new WrongStateException("Feedback can only be submitted for accepted matches");
        }
        if(feedbackRepository.findByRecommendation(recommendation) != null){
            throw new WrongStateException("Feedback already submitted for this recommendation");
        }
    }
}