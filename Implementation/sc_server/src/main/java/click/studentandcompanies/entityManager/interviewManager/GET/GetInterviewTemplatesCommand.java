package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.List;

public class GetInterviewTemplatesCommand implements InterviewManagerCommand<List<InterviewTemplate>> {
    private final String companyId;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final UserManager userManager;

    public GetInterviewTemplatesCommand(String companyId, InterviewTemplateRepository interviewTemplateRepository, UserManager userManager) {
        this.companyId = companyId;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.userManager = userManager;
    }

    @Override
    public List<InterviewTemplate> execute() {
        UserType userType = userManager.getUserType(companyId);
        List<InterviewTemplate> templates;
        if(userType == UserType.COMPANY) templates = interviewTemplateRepository.getInterviewTemplateByCompany_Id(companyId);
        else throw new UnauthorizedException("User is not a company");
        if(templates.isEmpty()) throw new NoContentException("No templates found");
        else return templates;
    }
}
