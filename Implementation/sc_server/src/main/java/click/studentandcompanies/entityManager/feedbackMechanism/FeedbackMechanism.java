package click.studentandcompanies.entityManager.feedbackMechanism;

import click.studentandcompanies.entity.Feedback;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.FeedbackRepository;
import click.studentandcompanies.entityManager.feedbackMechanism.feedbackMechanismCommands.SubmitFeedbackCommand;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FeedbackMechanism {
    private final UserManager userManager;
    private final FeedbackRepository feedbackRepository;

    public FeedbackMechanism(UserManager userManager, FeedbackRepository feedbackRepository) {
        this.userManager = userManager;
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback submitFeedback(Integer recommendationID, Map<String, Object> payload) {
        return new SubmitFeedbackCommand(recommendationID, payload, feedbackRepository, userManager).execute();
    }
}
