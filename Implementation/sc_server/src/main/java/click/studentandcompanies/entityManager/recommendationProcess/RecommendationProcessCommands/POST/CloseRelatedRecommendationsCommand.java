package click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommands.POST;

import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcessCommand;
import click.studentandcompanies.entityRepository.RecommendationRepository;

public class CloseRelatedRecommendationsCommand implements RecommendationProcessCommand<Void> {

    private final RecommendationRepository recommendationRepository;
    private final Integer internshipID;

    public CloseRelatedRecommendationsCommand(RecommendationRepository recommendationRepository, Integer internshipID) {
        this.recommendationRepository = recommendationRepository;
        this.internshipID = internshipID;
    }

    @Override
    public Void execute() {
        recommendationRepository.removeRecommendationByInternshipOffer_Id(internshipID);
        return null;
    }
}
