package click.studentandcompanies.entityManager.interviewManager;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.InterviewRepository;
import click.studentandcompanies.entityManager.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.entityManager.interviewManager.POST.sendInterviewAnswerCommand;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class InterviewManager {
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final InternshipPosOfferRepository internshipPosOfferRepository;

    public InterviewManager(InterviewRepository interviewRepository, UserManager userManager, InterviewTemplateRepository interviewTemplateRepository, InternshipPosOfferRepository internshipPosOfferRepository) {
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
    }

    public Interview sendInterviewAnswer(int interviewID, Map<String, Object> payload) throws IllegalArgumentException, IllegalCallerException {
        return new sendInterviewAnswerCommand(interviewID, payload, userManager, interviewRepository).execute();
    }
}
