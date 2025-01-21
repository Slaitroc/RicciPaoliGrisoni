package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.CvRepository;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;

public class GetCvByStudentCommand {
    CvRepository cvRepository;
    UserManager userManager;
    Integer studentID;

    public GetCvByStudentCommand(CvRepository cvRepository, UserManager userManager, Integer studentID) {
        this.cvRepository = cvRepository;
        this.userManager = userManager;
        this.studentID = studentID;
    }

    public Cv execute() throws NotFoundException, NoContentException {
        if (userManager.getStudentById(studentID) == null) {
            System.out.println("Student not found");
            throw new NotFoundException("Student not found");
        }
        Cv cv = cvRepository.getCvByStudent_Id(studentID);
        if(cv == null) throw new NoContentException("No Cv found");
        return cv;
    }
}
