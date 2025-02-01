package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.List;

public class GetInterviewTemplateCommand implements InterviewManagerCommand<InterviewTemplate> {
    private final Integer templateID;
    private final String companyID;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final UserManager userManager;

    public GetInterviewTemplateCommand(Integer templateID, String companyID, InterviewTemplateRepository interviewTemplateRepository, UserManager userManager) {
        this.templateID = templateID;
        this.companyID = companyID;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.userManager = userManager;
    }

    @Override
    public InterviewTemplate execute() {
        UserType userType = userManager.getUserType(companyID);
        if(userType != UserType.COMPANY){
            throw new BadInputException("Only companies can get their interview templates");
        }
        if(userManager.getCompanyById(companyID) == null){
            throw new NotFoundException("No company found with this id");
        }
        InterviewTemplate interviewTemplate = interviewTemplateRepository.findById(templateID).orElse(null);
        if(interviewTemplate == null){
            throw new NotFoundException("Interview template not found");
        }
        List<Interview> interviews = userManager.getInterviewsByCompanyID(companyID);
        if(interviews.stream().noneMatch(interview -> interview.getInterviewTemplate().getId().equals(templateID))){
            throw new UnauthorizedException("You are not authorized to get this interview template");
        }
        return interviewTemplate;
    }
}
