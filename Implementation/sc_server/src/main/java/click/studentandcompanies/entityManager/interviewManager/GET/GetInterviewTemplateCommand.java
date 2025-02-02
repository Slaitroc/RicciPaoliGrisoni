package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
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
    private final InterviewRepository interviewRepository;

    public GetInterviewTemplateCommand(Integer templateID, String userID, InterviewTemplateRepository interviewTemplateRepository, UserManager userManager, InterviewRepository interviewRepository) {
        this.templateID = templateID;
        this.userID = userID;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.userManager = userManager;
        this.interviewRepository = interviewRepository;
    }

    @Override
    public InterviewTemplate execute() {
        UserType userType = userManager.getUserType(userID);
        if(userType != UserType.COMPANY && userType != UserType.STUDENT){
            throw new BadInputException("Only companies and student can get their interview templates");
        }
        InterviewTemplate interviewTemplate = interviewTemplateRepository.findById(templateID).orElse(null);
        if(interviewTemplate == null){
            throw new NotFoundException("Interview template not found");
        }
        List<Interview> interviews = interviewRepository.findAll();
        if(userType == UserType.COMPANY) {
            for (Interview i : interviews) {
                Recommendation recommendation = i.getRecommendation();
                SpontaneousApplication spontaneousApplication = i.getSpontaneousApplication();
                if (recommendation != null && recommendation.getInternshipOffer().getCompany().getId().equals(userID)) {
                    return interviewTemplate;
                } else if (spontaneousApplication != null && spontaneousApplication.getInternshipOffer().getCompany().getId().equals(userID)) {
                    return interviewTemplate;
                }
            }
        }else{
            for(Interview i : interviews){
                Recommendation recommendation = i.getRecommendation();
                SpontaneousApplication spontaneousApplication = i.getSpontaneousApplication();
                if(recommendation != null && recommendation.getCv().getStudent().getId().equals(userID)){
                    return interviewTemplate;
                }else if(spontaneousApplication != null && spontaneousApplication.getStudent().getId().equals(userID)){
                    return interviewTemplate;
                }
            }
        }
        throw new UnauthorizedException("User not authorized to access this interview template");
    }
}
