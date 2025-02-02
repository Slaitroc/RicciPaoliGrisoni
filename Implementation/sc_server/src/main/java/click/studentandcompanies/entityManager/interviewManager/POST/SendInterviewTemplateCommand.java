package click.studentandcompanies.entityManager.interviewManager.POST;

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
import click.studentandcompanies.utils.exception.UnauthorizedException;

public class SendInterviewTemplateCommand implements InterviewManagerCommand<Interview>{
    private final Integer interviewID;
    private final Integer templateID;
    private final String companyID;
    private final UserManager userManager;
    private final InterviewRepository interviewRepository;
    private final InterviewTemplateRepository interviewTemplateRepository;

    public SendInterviewTemplateCommand(Integer interviewID, Integer templateID, String companyID, UserManager userManager, InterviewRepository interviewRepository, InterviewTemplateRepository interviewTemplateRepository) {
        this.interviewID = interviewID;
        this.templateID = templateID;
        this.companyID = companyID;
        this.userManager = userManager;
        this.interviewRepository = interviewRepository;
        this.interviewTemplateRepository = interviewTemplateRepository;
    }

    @Override
    public Interview execute() {
        UserType userType = userManager.getUserType(companyID);
        if(userType != UserType.COMPANY){
            throw new BadInputException("User is not a company");
        }
        InterviewTemplate interviewTemplate = interviewTemplateRepository.findById(templateID).orElse(null);
        if(interviewTemplate == null){
            throw new BadInputException("Template not found");
        }
        Interview interview = interviewRepository.findById(interviewID).orElse(null);
        if(interview == null){
            throw new BadInputException("Interview not found");
        }
        Recommendation recommendation = interview.getRecommendation();
        SpontaneousApplication spontaneousApplication = interview.getSpontaneousApplication();
        if(recommendation != null && !recommendation.getInternshipOffer().getCompany().getId().equals(companyID)){
            throw new UnauthorizedException("This company is not allowed to send interview to this student");
        }else if(spontaneousApplication != null && !spontaneousApplication.getInternshipOffer().getCompany().getId().equals(companyID)){
            throw new UnauthorizedException("This company is not allowed to send interview to this student");
        }
        interview.setInterviewTemplate(interviewTemplate);
        return interviewRepository.save(interview);
    }
}
