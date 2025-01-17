package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.entityRepository.RecommendationRepository;
import click.studentandcompanies.utils.UserType;
import org.springframework.stereotype.Service;

@Service
public class RecommendationProcess {
    private final UserManager userManager;
    private final RecommendationRepository recommendationRepository;

    public RecommendationProcess(UserManager userManager, RecommendationRepository recommendationRepository) {
        this.userManager = userManager;
        this.recommendationRepository = recommendationRepository;
    }

    //Handle the acceptance of a recommendation
    //Thx @Matteo for the Exception handling idea, it's much cleaner now
    public Recommendation acceptRecommendation(Integer recommendationID, Integer userID) throws IllegalCallerException{
        Recommendation recommendation = recommendationRepository.getRecommendationById(recommendationID);
        if (recommendation == null) {
            throw new IllegalCallerException("Recommendation not found");
        }
        UserType userType = userManager.getUserType(userID);
        if(userType == UserType.UNKNOWN){
            throw new IllegalCallerException("Unknown user type");
        }else if(userType == UserType.UNIVERSITY){
            throw new IllegalCallerException("Universities can't accept recommendations");
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

    public Recommendation refuseRecommendation(Integer recommendationID, Integer userID) throws IllegalCallerException {
        Recommendation recommendation = recommendationRepository.getRecommendationById(recommendationID);
        if (recommendation == null) {
            throw new IllegalCallerException("Recommendation not found");
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