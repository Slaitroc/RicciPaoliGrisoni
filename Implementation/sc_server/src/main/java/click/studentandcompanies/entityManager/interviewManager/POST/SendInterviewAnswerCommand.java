package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewQuiz;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.InterviewQuizRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.Map;
import java.util.Set;

public class SendInterviewAnswerCommand implements SubmissionManagerCommand<Interview> {
    private final int interviewID;
    private final Map<String, Object> payload;
    private final UserManager userManager;
    private final InterviewRepository interviewRepository;
    private final InterviewQuizRepository interviewQuizRepository;

    public SendInterviewAnswerCommand(int interviewID, Map<String, Object> payload, UserManager userManager, InterviewRepository interviewRepository, InterviewQuizRepository interviewQuizRepository) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.userManager = userManager;
        this.interviewRepository = interviewRepository;
        this.interviewQuizRepository = interviewQuizRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Interview execute() {
        Interview interview = interviewRepository.getInterviewById(interviewID);
        if(interview == null){
            throw new NotFoundException("Interview not found");
        }
        if(payload.get("student_id") == null){
            throw new BadInputException("Bad student id");
        }
        if(userManager.getStudentById((String) payload.get("student_id"))==null){
            throw new NotFoundException("Student not found");
        }
        Map<String, String> answer = (Map<String, String>) payload.get("answer");
        if(answer == null){
            throw new BadInputException("Bad answer");
        }
        if(!hasAllAnswer(payload)){
            throw new BadInputException("Missing answer");
        }
        InterviewQuiz interviewQuiz = createInterviewQuiz(interview, payload);
        interview.setInterviewQuiz(interviewQuiz);
        return interviewRepository.save(interview);
    }

    private boolean hasAllAnswer(Map<String, Object> payload) {
        Set<String> requiredKeys = Set.of(
                "answer1", "answer2", "answer3", "answer4", "answer5", "answer6" );
        return payload.keySet().containsAll(requiredKeys);
    }

    private InterviewQuiz createInterviewQuiz(Interview interview, Map<String, Object> payload) {
        InterviewQuiz interviewQuiz = new InterviewQuiz(
                (String) payload.get("answer1"),
                (String) payload.get("answer2"),
                (String) payload.get("answer3"),
                (String) payload.get("answer4"),
                (String) payload.get("answer5"),
                (String) payload.get("answer6")
        );
        interviewQuiz.setInterview(interview);
        return interviewQuizRepository.save(interviewQuiz);
    }
}
