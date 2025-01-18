package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetStudentCVCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final int studentID;
    private final SubmissionManager submissionManager;

    public GetStudentCVCommandCall(int studentID, SubmissionManager submissionManager) {
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
