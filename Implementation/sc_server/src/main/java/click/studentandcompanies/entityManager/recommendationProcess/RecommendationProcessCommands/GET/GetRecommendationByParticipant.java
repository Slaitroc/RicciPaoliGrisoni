package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.GET;

import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.RecommendationRepository;
import click.studentandcompanies.utils.UserType;

import java.util.List;

public class GetRecommendationByParticipant {

    UserManager userManager;
    RecommendationRepository recommendationRepository;
    Integer userID;

    public GetRecommendationByParticipant(UserManager userManager, RecommendationRepository recommendationRepository, Integer userID) {
        this.userManager = userManager;
        this.recommendationRepository = recommendationRepository;
        this.userID = userID;
    }

    public List<Recommendation> execute() {
        UserType type = userManager.getUserType(userID);
        List<Recommendation> recommendations;
        switch (type) {
            case STUDENT -> {
                recommendations = recommendationRepository.findRecommendationByStudentId(userID);
                System.out.println("Recommendations found for student: " + recommendations);
            }
            case COMPANY -> {
                recommendations = recommendationRepository.findRecommendationByCompanyId(userID);
                System.out.println("Recommendations found for company: " + recommendations);
            }
            case UNIVERSITY -> throw new IllegalCallerException("User is not a company or student");
            default -> throw new IllegalArgumentException("User not found");
        }
        return recommendations;
    }
}