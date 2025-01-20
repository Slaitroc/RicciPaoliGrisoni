package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;

import java.util.List;

public class getSpontaneousApplicationByStudentCommand implements SubmissionManagerCommand {

    SpontaneousApplicationRepository spontaneousApplicationRepository;
    UserManager userManager;
    Integer studentID;

    public getSpontaneousApplicationByStudentCommand(SpontaneousApplicationRepository spontaneousApplicationRepository, UserManager userManager, Integer studentID) {
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.userManager = userManager;
        this.studentID = studentID;
    }

    @Override
    public List<SpontaneousApplication> execute() throws IllegalArgumentException{
        if (userManager.getStudentById(studentID) == null) {
            System.out.println("Student not found");
            throw new IllegalArgumentException("Student not found");
        }
        return spontaneousApplicationRepository.getSpontaneousApplicationByStudent_Id(studentID);
    }
}
