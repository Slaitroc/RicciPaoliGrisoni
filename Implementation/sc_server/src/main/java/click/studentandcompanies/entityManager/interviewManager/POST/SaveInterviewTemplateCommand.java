package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.Map;

public class SaveInterviewTemplateCommand implements InterviewManagerCommand<InterviewTemplate> {
    private final int InterviewID;
    private final Map<String, Object> payload;
    private final InterviewRepository interviewRepository;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final UserManager userManager;

    public SaveInterviewTemplateCommand(int interviewID, Map<String, Object> payload, InterviewRepository interviewRepository, InterviewTemplateRepository interviewTemplateRepository, UserManager userManager) {
        InterviewID = interviewID;
        this.payload = payload;
        this.interviewRepository = interviewRepository;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.userManager = userManager;
    }

    @Override
    public InterviewTemplate execute() {
        Interview interview = interviewRepository.getInterviewById(InterviewID);
        if(interview == null){
            System.out.println("Interview not found");
            throw new NotFoundException("Interview not found");
        }
        if(payload.get("company_id")==null){
            System.out.println("Company id not found");
            throw new NotFoundException("Company id not found");
        }
        Company company = userManager.getCompanyById((String) payload.get("company_id"));
        InterviewTemplate interviewTemplate = InterviewManager.createInterviewTemplate(payload, company);
        return interviewTemplateRepository.save(interviewTemplate);
    }
}
