package click.studentandcompanies.entityManager.interviewManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.GET.*;
import click.studentandcompanies.entityManager.interviewManager.POST.*;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityRepository.InterviewQuizRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityManager.interviewManager.POST.SendInterviewAnswerCommand;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InterviewManager {
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final InternshipPosOfferRepository internshipPosOfferRepository;
    private final InterviewQuizRepository interviewQuizRepository;

    public InterviewManager(InterviewRepository interviewRepository, UserManager userManager, InterviewTemplateRepository interviewTemplateRepository, InternshipPosOfferRepository internshipPosOfferRepository, InterviewQuizRepository interviewQuizRepository) {
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
        this.interviewQuizRepository = interviewQuizRepository;
    }

    public Interview sendInterviewAnswer(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SendInterviewAnswerCommand(interviewID, payload, userManager, interviewRepository, interviewQuizRepository).execute();
    }

    public Interview sendInterview(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SendInterviewCommand(interviewID, payload, userManager, interviewRepository, interviewTemplateRepository).execute();
    }

    public InterviewTemplate saveInterviewTemplate(int InterviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SaveInterviewTemplateCommand(InterviewID, payload, interviewRepository, interviewTemplateRepository, userManager).execute();
    }

    //Because createInterviewTemplate is needed by both the sendInterview and saveInterviewTemplate methods, it is extracted to a separate method
//    @SuppressWarnings("unchecked")
    /*public static InterviewTemplate createInterviewTemplate(Map<String, Object> payload, Company company) {
        InterviewTemplate interviewTemplate = new InterviewTemplate();
        interviewTemplate.setCompany(company);
        Map<String, String> questions = (Map<String, String>) payload.get("questions");
        if(questions == null){
            throw new BadInputException("Bad answer");
        }
        interviewTemplate.setQuestions(questions.toString());
        return interviewTemplate;
    }*/

    public Interview sendInterviewTemplate(int interviewID, int templateID, String companyID) throws NotFoundException, BadInputException, UnauthorizedException {
        return new SendInterviewTemplateCommand(interviewID, templateID, companyID, userManager, interviewRepository, interviewTemplateRepository).execute();
    }

    public List<InterviewTemplate> getInterviewTemplates(String companyId) {
        return new GetInterviewTemplatesCommand(companyId, interviewRepository, userManager).execute();
    }

    public Interview evaluateInterview(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new EvaluateInterviewCall(interviewID, payload, interviewRepository, interviewQuizRepository, internshipPosOfferRepository, userManager).execute();
    }

    public InternshipPosOffer sendInterviewPositionOffer(int interviewID, Map<String, Object> payload) throws NotFoundException, BadInputException {
        return new SendInterviewPositionOfferCommand(interviewID, payload, userManager, interviewRepository, internshipPosOfferRepository).execute();
    }

    public InternshipPosOffer acceptInternshipPositionOffer(Integer intPosOffID, Map<String, Object> payload) throws NotFoundException, BadInputException, UnauthorizedException, WrongStateException {
        return new AcceptInternshipPositionOfferCommand(intPosOffID, payload, internshipPosOfferRepository, userManager).execute();
    }

    public InternshipPosOffer rejectInternshipPositionOffer(Integer intPosOffID, Map<String, Object> payload) throws NotFoundException, BadInputException, UnauthorizedException, WrongStateException {
        return new RejectInternshipPositionOfferCommand(intPosOffID, payload, internshipPosOfferRepository, userManager).execute();
    }

    public List<Interview> getInterview(String userID) throws NotFoundException, BadInputException {
        return new GetInterviewsCall(userID, interviewRepository, userManager).execute();
    }

    public List<InternshipPosOffer> getInterviewPosOffersOfUser(String userID) throws BadInputException, NotFoundException {
        return new GetInternshipPosOfferCommand(userID, userManager).execute();
    }

    public List<Interview> getMatchNotInterviewed(String companyID) throws BadInputException, NotFoundException{
        return new GetMatchNotInterviewedCommand(interviewRepository, userManager, companyID).execute();
    }

    public Interview getSpecificInterview(int interviewID, String userID) throws NotFoundException, UnauthorizedException {
        return new GetSpecificInterviewCommand(interviewID, userID, interviewRepository).execute();
    }

    public InterviewTemplate getInterviewTemplate(int templateID, String userID) throws NotFoundException, BadInputException {
        return new GetInterviewTemplateCommand(templateID, userID, interviewTemplateRepository, userManager, interviewRepository ).execute();
    }

    public InterviewQuiz getInterviewQuiz(int quizID, String userID) throws NotFoundException, BadInputException, UnauthorizedException {
        return new GetInterviewQuizCommand(quizID, userID, interviewRepository, interviewQuizRepository, userManager).execute();
    }
}
