package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.Map;

public class SendInterviewAnswerCommand implements SubmissionManagerCommand<Interview> {
    int interviewID;
    Map<String, Object> payload;
    UserManager userManager;
    InterviewRepository interviewRepository;

    public SendInterviewAnswerCommand(int interviewID, Map<String, Object> payload, UserManager userManager, InterviewRepository interviewRepository) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.userManager = userManager;
        this.interviewRepository = interviewRepository;
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
        Student student = userManager.getStudentById((String) payload.get("student_id"));
        if(student == null){
            throw new NotFoundException("Student not found");
        }
        Map<String, String> answer = (Map<String, String>) payload.get("answer");
        if(answer == null){
            throw new BadInputException("Bad answer");
        }
        interview.setAnswer(answer.toString());
        return interviewRepository.save(interview);
    }
}
