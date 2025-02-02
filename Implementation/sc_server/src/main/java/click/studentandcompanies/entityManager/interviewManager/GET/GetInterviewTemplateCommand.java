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
    private final String userID;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final UserManager userManager;

    public GetInterviewTemplateCommand(Integer templateID, String userID, InterviewTemplateRepository interviewTemplateRepository, UserManager userManager) {
        this.templateID = templateID;
        this.userID = userID;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.userManager = userManager;
    }

    @Override
    public InterviewTemplate execute() {
        UserType userType = userManager.getUserType(userID);
        if(userType != UserType.COMPANY && userType != UserType.STUDENT){
            throw new BadInputException("Only companies and student can get their interview templates");
        }
        if(userManager.getCompanyById(userID) == null){
            throw new NotFoundException("No company found with this id");
        }
        InterviewTemplate interviewTemplate = interviewTemplateRepository.findById(templateID).orElse(null);
        if(interviewTemplate == null){
            throw new NotFoundException("Interview template not found");
        }
        List<Interview> interviews = userManager.getInterviewsByCompanyID(userID);
        if(interviews.stream().noneMatch(interview -> interview.getInterviewTemplate().getId().equals(templateID))){
            throw new UnauthorizedException("You are not authorized to get this interview template");
        }
        return interviewTemplate;
    }
}
