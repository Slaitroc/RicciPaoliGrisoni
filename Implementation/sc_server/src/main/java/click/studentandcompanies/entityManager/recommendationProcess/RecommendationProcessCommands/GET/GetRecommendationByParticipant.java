package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.GET;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.List;

public class GetRecommendationByParticipant implements RecommendationProcessCommand<List<Recommendation>> {

    UserManager userManager;
    RecommendationRepository recommendationRepository;
    Integer userID;

    public GetRecommendationByParticipant(UserManager userManager, RecommendationRepository recommendationRepository, Integer userID) {
        this.userManager = userManager;
        this.recommendationRepository = recommendationRepository;
        this.userID = userID;
    }

    public List<Recommendation> execute() throws NotFoundException, NoContentException, BadInputException {
        UserType type = userManager.getUserType(userID);
        List<Recommendation> recommendations = switch (type) {
            case STUDENT -> recommendationRepository.findRecommendationByStudentId(userID);
            case COMPANY -> recommendationRepository.findRecommendationByCompanyId(userID);
            case UNIVERSITY -> throw new BadInputException("User is not a company or student");
            default -> throw new NotFoundException("User not found");
        };
        if (recommendations.isEmpty()) throw new NoContentException("No recommendations found");
        return recommendations;
    }
}