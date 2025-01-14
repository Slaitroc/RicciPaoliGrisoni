package click.studentandcompanies.entityManager;

import click.studentandcompanies.entityManager.entityRepository.FeedbackRepository;

public class FeedbackMechanism {
    private final UserManager userManager;
    private final FeedbackRepository feedbackRepository;

    public FeedbackMechanism(UserManager userManager, FeedbackRepository feedbackRepository) {
        this.userManager = userManager;
        this.feedbackRepository = feedbackRepository;
    }
}
