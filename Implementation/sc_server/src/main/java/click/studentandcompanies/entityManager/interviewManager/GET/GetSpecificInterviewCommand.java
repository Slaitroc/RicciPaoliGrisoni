package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

public class GetSpecificInterviewCommand implements InterviewManagerCommand<Interview> {
    private final Integer interviewID;
    private final String userID;
    private final InterviewRepository interviewRepository;

    public GetSpecificInterviewCommand(Integer interviewID, String userID, InterviewRepository interviewRepository) {
        this.interviewID = interviewID;
        this.userID = userID;
        this.interviewRepository = interviewRepository;
    }

    @Override
    public Interview execute() {
        Interview interview = interviewRepository.findById(interviewID).orElse(null);
        if (interview == null) {
            throw new NotFoundException("Interview not found");
        }
        Recommendation recommendation = interview.getRecommendation();
        SpontaneousApplication spontaneousApplication = interview.getSpontaneousApplication();
        if(recommendation != null && !recommendation.getCv().getStudent().getId().equals(userID) && !recommendation.getCv().getStudent().getUniversity().getId().equals(userID) && !recommendation.getInternshipOffer().getCompany().getId().equals(userID)) {
            throw new UnauthorizedException("User not authorized to access this interview");
        }else if(spontaneousApplication != null && !spontaneousApplication.getStudent().getId().equals(userID) && !spontaneousApplication.getStudent().getUniversity().getId().equals(userID) && !spontaneousApplication.getInternshipOffer().getCompany().getId().equals(userID)) {
            throw new UnauthorizedException("User not authorized to access this interview");
        }
        return interview;
    }

}
