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
        try{
            Cv studentCV = submissionManager.getCvByStudent(studentID);
            if (studentCV == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.CV, studentCV), HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
