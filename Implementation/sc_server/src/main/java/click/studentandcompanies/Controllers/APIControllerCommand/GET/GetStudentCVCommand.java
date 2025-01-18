package click.studentandcompanies.Controllers.APIControllerCommand.GET;

import click.studentandcompanies.Controllers.APIControllerCommand.APIControllerCommand;
import click.studentandcompanies.DTO.DTO;
import click.studentandcompanies.DTO.DTOCreator;
import click.studentandcompanies.DTO.DTOTypes;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.SubmissionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class GetStudentCVCommand implements APIControllerCommand<ResponseEntity<DTO>> {
    private final int studentID;
    private final SubmissionManager submissionManager;

    public GetStudentCVCommand(int studentID, SubmissionManager submissionManager) {
        this.studentID = studentID;
        this.submissionManager = submissionManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        Cv studentCV = submissionManager.getCvByStudent(studentID);
        if (studentCV == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.CV, studentCV), HttpStatus.OK);
    }
}
