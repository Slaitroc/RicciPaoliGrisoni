package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.POST;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.Map;

public class AcceptRecommendationCommand implements RecommendationProcessCommand<Recommendation> {
    private final UserManager userManager;
    private final Integer recommendationID;
    private final RecommendationRepository recommendationRepository;
    private final Map<String, Object> payload;

    public AcceptRecommendationCommand(UserManager userManager, Integer recommendationID, Map<String, Object> payload, RecommendationRepository recommendationRepository) {
        this.userManager = userManager;
        this.recommendationID = recommendationID;
        this.payload = payload;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Recommendation execute() {
        Recommendation recommendation = recommendationRepository.getRecommendationById(recommendationID);
        if (recommendation == null) {
            throw new NotFoundException("Recommendation with ID " + recommendationID + " not found");
        }
        if(payload.get("user_id")==null){
            throw new BadInputException("User id not found");
        }
        UserType userType = userManager.getUserType((Integer) payload.get("user_id"));
        if(userType == UserType.UNKNOWN){
            throw new BadInputException("Unknown user type");
        }else if(userType == UserType.UNIVERSITY){
            throw new BadInputException("Universities can't accept recommendations");
        }

        checkIfResponseAlreadySent(recommendation, userType);

        if(recommendation.getStatus() == RecommendationStatusEnum.pendingMatch){
            if(userType == UserType.STUDENT){
                recommendation.setStatus(RecommendationStatusEnum.acceptedByStudent);
            }else{
                recommendation.setStatus(RecommendationStatusEnum.acceptedByCompany);
            }
        }else{
            recommendation.setStatus(RecommendationStatusEnum.acceptedMatch);
        }
        recommendationRepository.save(recommendation);
        return recommendation;
    }

    private void checkIfResponseAlreadySent(Recommendation recommendation, UserType userType) throws IllegalCallerException {
        if((userType == UserType.STUDENT && recommendation.getStatus() == RecommendationStatusEnum.acceptedByStudent) ||
                (userType == UserType.COMPANY && recommendation.getStatus() == RecommendationStatusEnum.acceptedByCompany) ||
                recommendation.getStatus() == RecommendationStatusEnum.acceptedMatch){
            throw new BadInputException("Recommendation already accepted");
        }else if(recommendation.getStatus() == RecommendationStatusEnum.refusedMatch){
            throw new BadInputException("Recommendation already refused");
        }
    }
}
