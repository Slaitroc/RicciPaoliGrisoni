package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.GET;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.RecommendationRepository;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.List;

public class GetRecommendationByParticipantCommand implements RecommendationProcessCommand<List<Recommendation>> {
    UserManager userManager;
    RecommendationRepository recommendationRepository;
    String userID;

    public GetRecommendationByParticipantCommand(UserManager userManager, RecommendationRepository recommendationRepository, String userID) {
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
        recommendations = recommendations.stream().filter(recommendation -> recommendation.getStatus() != RecommendationStatusEnum.rejectedMatch).toList();
        if(type == UserType.STUDENT){
            recommendations = recommendations.stream().filter(recommendation -> recommendation.getStatus() != RecommendationStatusEnum.acceptedByStudent).toList();
        }else{
            recommendations = recommendations.stream().filter(recommendation -> recommendation.getStatus() != RecommendationStatusEnum.acceptedByCompany).toList();
        }
        //todo check if in the front end a empty list is a valid response
        if (recommendations.isEmpty()) throw new NoContentException("No eligible recommendations found at the moment, try again later");
        return recommendations;
    }
}