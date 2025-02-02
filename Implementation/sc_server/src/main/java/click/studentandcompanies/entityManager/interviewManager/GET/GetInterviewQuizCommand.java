package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewQuizRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.List;

public class GetInterviewQuizCommand implements InterviewManagerCommand<InterviewQuiz> {
    private final int quizID;
    private final String userID;
    private final InterviewRepository interviewRepository;
    private final InterviewQuizRepository interviewQuizRepository;
    private final UserManager userManager;

    public GetInterviewQuizCommand(int quizID, String userID, InterviewRepository interviewRepository, InterviewQuizRepository interviewQuizRepository, UserManager userManager) {
        this.quizID = quizID;
        this.userID = userID;
        this.interviewRepository = interviewRepository;
        this.interviewQuizRepository = interviewQuizRepository;
        this.userManager = userManager;
    }

    @Override
    public InterviewQuiz execute() {
        UserType userType = userManager.getUserType(userID);
        if(userType != UserType.COMPANY && userType != UserType.STUDENT){
            throw new BadInputException("User is not a company or a student");
        }
        InterviewQuiz interviewQuiz = interviewQuizRepository.findById(quizID).orElse(null);
        if(interviewQuiz == null){
            throw new NotFoundException("Interview quiz not found");
        }
        Interview interview = interviewQuiz.getInterview();
        Recommendation recommendation = interview.getRecommendation();
        SpontaneousApplication spontaneousApplication = interview.getSpontaneousApplication();
        if(recommendation != null){
            if(userType == UserType.COMPANY && !recommendation.getInternshipOffer().getCompany().getId().equals(userID)){
                throw new UnauthorizedException("User not authorized to access this interview quiz");
            }else if(userType == UserType.STUDENT && !recommendation.getCv().getStudent().getId().equals(userID)){
                throw new UnauthorizedException("User not authorized to access this interview quiz");
            }
        }else{
            if(userType == UserType.COMPANY && !spontaneousApplication.getInternshipOffer().getCompany().getId().equals(userID)){
                throw new UnauthorizedException("User not authorized to access this interview quiz");
            }else if(userType == UserType.STUDENT && !spontaneousApplication.getStudent().getId().equals(userID)){
                throw new UnauthorizedException("User not authorized to access this interview quiz");
            }
        }
        return interviewQuiz;
    }
}
