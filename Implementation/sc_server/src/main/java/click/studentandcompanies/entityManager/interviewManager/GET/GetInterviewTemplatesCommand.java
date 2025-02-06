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
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetInterviewTemplatesCommand implements InterviewManagerCommand<List<InterviewTemplate>> {
    private final String companyId;
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;

    public GetInterviewTemplatesCommand(String companyId, InterviewRepository interviewRepository, UserManager userManager) {
        this.companyId = companyId;
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
    }

    @Override
    public List<InterviewTemplate> execute() {
        UserType userType = userManager.getUserType(companyId);
        if(userType != UserType.COMPANY){
            throw new BadInputException("Only companies can get their interview templates");
        }
        if(userManager.getCompanyById(companyId) == null){
            throw new NoContentException("No company found with this id");
        }
        List<Interview> allInterviews = interviewRepository.findAll();
        List<Interview> okInterviews = new ArrayList<>();
        for(Interview interview : allInterviews){
            Recommendation recommendation = interview.getRecommendation();
            SpontaneousApplication spontaneousApplication = interview.getSpontaneousApplication();
            if(recommendation != null && recommendation.getInternshipOffer().getCompany().getId().equals(companyId)){
                okInterviews.add(interview);
            } else if(spontaneousApplication != null && spontaneousApplication.getInternshipOffer().getCompany().getId().equals(companyId)){
                okInterviews.add(interview);
            }
        }
        return okInterviews.stream().map(Interview::getInterviewTemplate).filter(Objects::nonNull).distinct().toList();
    }
}
