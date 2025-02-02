package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewQuiz;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.List;

public class GetInterviewQuizCommand implements InterviewManagerCommand<InterviewQuiz> {
    private final int interviewID;
    private final String userID;
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;

    public GetInterviewQuizCommand(int interviewID, String userID, InterviewRepository interviewRepository, UserManager userManager) {
        this.interviewID = interviewID;
        this.userID = userID;
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
    }

    @Override
    public InterviewQuiz execute() {
        UserType userType = userManager.getUserType(userID);
        if(userType != UserType.COMPANY && userType != UserType.STUDENT){
            throw new BadInputException("User is not a company or a student");
        }
        Interview interview = interviewRepository.findById(interviewID).orElse(null);
        if(interview == null){
            throw new BadInputException("Interview not found");
        }
        InterviewQuiz interviewQuiz = interview.getInterviewQuiz();
        if(interviewQuiz == null){
            throw new NotFoundException("Interview quiz not found");
        }
        List<Interview> interviews = interviewRepository.findAll();
        if(interviews.stream().map(Interview::getInterviewQuiz).noneMatch(interviewQuiz::equals)){
            throw new UnauthorizedException("User is not authorized to access this interview quiz");
        }
        return interviewQuiz;
    }
}
