package click.studentandcompanies.entityManager;

import click.studentandcompanies.entityManager.entityRepository.RecommendationRepository;

public class RecommendationProcess {
    private final UserManager userManager;
    private final RecommendationRepository recommendationRepository;

    public RecommendationProcess(UserManager userManager, RecommendationRepository recommendationRepository) {
        this.userManager = userManager;
        this.recommendationRepository = recommendationRepository;
    }
}
