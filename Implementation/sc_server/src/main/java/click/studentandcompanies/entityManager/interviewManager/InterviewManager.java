package click.studentandcompanies.entityManager.interviewManager;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.POST.SendInterviewCommand;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.entityManager.interviewManager.POST.SendInterviewAnswerCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
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

    public Interview sendInterviewAnswer(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SendInterviewAnswerCommand(interviewID, payload, userManager, interviewRepository).execute();
    }

    public Interview sendInterview(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SendInterviewCommand(interviewID, payload, userManager, interviewRepository, interviewTemplateRepository).execute();
    }
}
