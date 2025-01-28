package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetStudentCVCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final String studentID;
    private final SubmissionManager submissionManager;
    private String userID;

    public GetStudentCVCommandCall(String studentID, String userID, SubmissionManager submissionManager) {
        this.studentID = studentID;
        this.userID = userID;
        this.submissionManager = submissionManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            if (userID == null) {
                return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, "User is not logged in"), HttpStatus.UNAUTHORIZED);
            }
            Cv studentCV = submissionManager.getCvByStudent(studentID);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.CV, studentCV), HttpStatus.OK);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (NoContentException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
