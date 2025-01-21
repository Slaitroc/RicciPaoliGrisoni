package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.Map;

public class SendInterviewCommand implements InterviewManagerCommand<Interview> {
    int InterviewID;
    Map<String, Object> payload;
    UserManager userManager;
    InterviewRepository interviewRepository;
    InterviewTemplateRepository interviewTemplateRepository;

    public SendInterviewCommand(int InterviewID, Map<String, Object> payload, UserManager userManager, InterviewRepository interviewRepository, InterviewTemplateRepository interviewTemplateRepository) {
        this.InterviewID = InterviewID;
        this.payload = payload;
        this.userManager = userManager;
        this.interviewRepository = interviewRepository;
        this.interviewTemplateRepository = interviewTemplateRepository;
    }

    @Override
    public Interview execute() {
        Interview interview = interviewRepository.getInterviewById(InterviewID);
        if(interview == null){
            System.out.println("Interview not found");
            throw new NotFoundException("Interview not found");
        }
        if(interview.getStatus() != InterviewStatusEnum.toBeSubmitted){
            System.out.println("Interview already submitted");
            throw new BadInputException("Interview already submitted");
        }
        InterviewTemplate interviewTemplate = createInterviewTemplate(payload);
        interview.setInterviewTemplate(interviewTemplate);
        interview.setStatus(InterviewStatusEnum.submitted);
        return interviewRepository.save(interview);
    }

    @SuppressWarnings("unchecked")
    private InterviewTemplate createInterviewTemplate(Map<String, Object> payload) {
        InterviewTemplate interviewTemplate = new InterviewTemplate();
        Map<String, String> questions = (Map<String, String>) payload.get("questions");
        if(questions == null){
            throw new BadInputException("Bad answer");
        }
        interviewTemplate.setQuestions(questions.toString());
        interviewTemplateRepository.save(interviewTemplate);
        return interviewTemplate;
    }
}
