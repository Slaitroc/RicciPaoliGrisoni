package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.POST;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.RecommendationRepository;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.utils.UserType;

public class refuseRecommendationCommand implements RecommendationProcessCommand<Recommendation> {
    UserManager userManager;
    Integer recommendationID;
    Integer userID;
    RecommendationRepository recommendationRepository;

    public refuseRecommendationCommand(UserManager userManager, Integer recommendationID, Integer userID, RecommendationRepository recommendationRepository) {
        this.userManager = userManager;
        this.recommendationID = recommendationID;
        this.userID = userID;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Recommendation execute() {
        Recommendation recommendation = recommendationRepository.getRecommendationById(recommendationID);
        if (recommendation == null) {
            throw new IllegalArgumentException("Recommendation with ID " + recommendationID + " not found");
        }
        UserType userType = userManager.getUserType(userID);
        if(userType == UserType.UNKNOWN){
            throw new IllegalCallerException("Unknown user type");
        }else if(userType == UserType.UNIVERSITY){
            throw new IllegalCallerException("Universities can't refuse recommendations");
        }

        checkIfResponseAlreadySent(recommendation, userType);

        recommendation.setStatus(RecommendationStatusEnum.refusedMatch);
        recommendationRepository.save(recommendation);
        return recommendation;
    }

    private void checkIfResponseAlreadySent(Recommendation recommendation, UserType userType) throws IllegalCallerException {
        if((userType == UserType.STUDENT && recommendation.getStatus() == RecommendationStatusEnum.acceptedByStudent) ||
                (userType == UserType.COMPANY && recommendation.getStatus() == RecommendationStatusEnum.acceptedByCompany) ||
                recommendation.getStatus() == RecommendationStatusEnum.acceptedMatch){
            throw new IllegalCallerException("Recommendation already accepted");
        }else if(recommendation.getStatus() == RecommendationStatusEnum.refusedMatch){
            throw new IllegalCallerException("Recommendation already refused");
        }
    }
}
