package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
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
        if(payload.get("company_id")==null){
            System.out.println("Company id not found");
            throw new BadInputException("Company id not found");
        }
        click.studentandcompanies.entity.Company company = userManager.getCompanyById((String) payload.get("company_id"));
        if(company == null){
            System.out.println("Company not found");
            throw new NotFoundException("Company not found");
        }
        InterviewTemplate interviewTemplate = InterviewManager.createInterviewTemplate(payload, company);
        interviewTemplateRepository.save(interviewTemplate);
        interview.setInterviewTemplate(interviewTemplate);
        interview.setStatus(InterviewStatusEnum.submitted);
        return interviewRepository.save(interview);
    }
}
