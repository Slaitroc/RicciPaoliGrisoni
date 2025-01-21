package click.studentandcompanies.entityManager.interviewManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.POST.*;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.entityManager.interviewManager.POST.SendInterviewAnswerCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;
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

    public InterviewTemplate saveInterviewTemplate(int InterviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SaveInterviewTemplateCommand(InterviewID, payload, interviewRepository, interviewTemplateRepository, userManager).execute();
    }

    //Because createInterviewTemplate is needed by both the sendInterview and saveInterviewTemplate methods, it is extracted to a separate method
    @SuppressWarnings("unchecked")
    public static InterviewTemplate createInterviewTemplate(Map<String, Object> payload, Company company) {
        InterviewTemplate interviewTemplate = new InterviewTemplate();
        interviewTemplate.setCompany(company);
        Map<String, String> questions = (Map<String, String>) payload.get("questions");
        if(questions == null){
            throw new BadInputException("Bad answer");
        }
        interviewTemplate.setQuestions(questions.toString());
        return interviewTemplate;
    }

    public Interview sendInterviewTemplate(int interviewID, int templateID, Map<String, Object> payload) throws NotFoundException, BadInputException, UnauthorizedException {
        return new SendInterviewTemplateCommand(interviewID, templateID, payload, interviewRepository, interviewTemplateRepository).execute();
    }

    public Interview evaluateInterview(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new EvaluateInterviewCall(interviewID, payload, interviewRepository).execute();
    }

    public InternshipPosOffer sendInterviewPositionOffer(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SendInterviewPositionOfferCommand(interviewID, payload, userManager, interviewRepository, internshipPosOfferRepository).execute();
    }

    public InternshipPosOffer acceptInternshipPositionOffer(Integer intPosOffID, Map<String, Object> payload) throws NotFoundException, BadInputException, UnauthorizedException, WrongStateException {
        return new AcceptInternshipPositionOfferCommand(intPosOffID, payload, interviewRepository, userManager).execute();
    }
}
