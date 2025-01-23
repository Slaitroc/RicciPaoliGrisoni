package click.studentandcompanies.entityManager.recommendationProcess;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.GET.*;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.POST.*;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public Recommendation acceptRecommendation(Integer recommendationID, Map<String, Object> payload) throws BadInputException, NotFoundException{
        return new AcceptRecommendationCommand(userManager, recommendationID, payload, recommendationRepository).execute();
    }

    public Recommendation refuseRecommendation(Integer recommendationID, Map<String, Object> payload) throws BadInputException, NotFoundException {
        return new RefuseRecommendationCommand(userManager, recommendationID, payload, recommendationRepository).execute();
    }

    public List<Recommendation> getRecommendationsByParticipant(Integer userID) throws BadInputException, NotFoundException, NoContentException {
        return new GetRecommendationByParticipant(userManager, recommendationRepository, userID).execute();
    }

    @Transactional
    public void closeRelatedRecommendations(Integer internshipID) {
        new CloseRelatedRecommendationsCommand(recommendationRepository, internshipID).execute();
    }
}