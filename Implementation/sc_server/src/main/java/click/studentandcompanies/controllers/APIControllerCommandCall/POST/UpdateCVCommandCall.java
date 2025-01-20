package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class UpdateCVCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final Map<String, Object> payload;
    private final SubmissionManager submissionManager;

    public UpdateCVCommandCall(Map<String, Object> payload, SubmissionManager submissionManager) {
        this.payload = payload;
        this.submissionManager = submissionManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            Cv cv = submissionManager.updateCvCall(payload);
            //todo: start the recommendation process
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.CV, cv), HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
