package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;

import java.util.ArrayList;
import java.util.List;

public class GetInterviewsCall implements InterviewManagerCommand<List<Interview>> {
    private final String userID;
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;

    public GetInterviewsCall(String userID, InterviewRepository interviewRepository, UserManager userManager) {
        this.userID = userID;
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
    }

    @Override
    public List<Interview> execute() {
        UserType userType = userManager.getUserType(userID);
        if(userType != UserType.STUDENT && userType != UserType.COMPANY){
            throw new BadInputException("User is either not a student or a company");
        }else {
            List<Interview> allInterviews = interviewRepository.findAll();
            List<Interview> okInterviews = new ArrayList<>();
            if (userType == UserType.STUDENT) {
                for (Interview interview : allInterviews) {
                    if (interview.getRecommendation() != null && interview.getRecommendation().getCv().getStudent().getId().equals(userID)) {
                        okInterviews.add(interview);
                    } else if (interview.getSpontaneousApplication() != null && interview.getSpontaneousApplication().getStudent().getId().equals(userID)) {
                        okInterviews.add(interview);
                    }
                }
            } else { // userType == UserType.COMPANY
                for (Interview interview : allInterviews) {
                    if (interview.getRecommendation() != null && interview.getRecommendation().getInternshipOffer().getCompany().getId().equals(userID)) {
                        okInterviews.add(interview);
                    } else if (interview.getSpontaneousApplication() != null && interview.getSpontaneousApplication().getInternshipOffer().getCompany().getId().equals(userID)) {
                        okInterviews.add(interview);
                    }
                }
            }
            return okInterviews;
        }
    }
}
