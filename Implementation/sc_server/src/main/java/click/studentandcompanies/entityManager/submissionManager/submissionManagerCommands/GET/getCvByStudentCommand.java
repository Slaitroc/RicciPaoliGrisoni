package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.CvRepository;

public class getCvByStudentCommand {
    CvRepository cvRepository;
    UserManager userManager;
    Integer studentID;

    public getCvByStudentCommand(CvRepository cvRepository, UserManager userManager, Integer studentID) {
        this.cvRepository = cvRepository;
        this.userManager = userManager;
        this.studentID = studentID;
    }

    public Cv execute() {
        if (userManager.getStudentById(studentID) == null) {
            System.out.println("Student not found");
            throw new IllegalArgumentException("Student not found");
        }
        return cvRepository.getCvByStudent_Id(studentID);
    }
}
