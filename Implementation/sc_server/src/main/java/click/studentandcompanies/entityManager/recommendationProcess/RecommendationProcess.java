package click.studentandcompanies.entityManager.recommendationProcess;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.RecommendationRepository;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.acceptRecommendationCommand;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.refuseRecommendationCommand;
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
    public Recommendation acceptRecommendation(Integer recommendationID, Integer userID) throws IllegalCallerException, IllegalArgumentException{
        return new acceptRecommendationCommand(userManager, recommendationID, userID, recommendationRepository).execute();
    }

    public Recommendation refuseRecommendation(Integer recommendationID, Integer userID) throws IllegalCallerException, IllegalArgumentException {
        return new refuseRecommendationCommand(userManager, recommendationID, userID, recommendationRepository).execute();
    }
}