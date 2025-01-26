package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetSpontaneousApplicationsCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {
    SubmissionManager submissionManager;
    String userID;

    public GetSpontaneousApplicationsCommandCall(String userID, SubmissionManager submissionManager) {
        this.submissionManager = submissionManager;
        this.userID = userID;
    }

    public ResponseEntity<List<DTO>>  execute() {
        List<DTO> dtos = new ArrayList<>();
        try {
            List<SpontaneousApplication> applications = submissionManager.getSpontaneousApplicationsByParticipant(userID);
            for(SpontaneousApplication application : applications){
                dtos.add(DTOCreator.createDTO(DTOTypes.SPONTANEOUS_APPLICATION, application));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);

        }catch (NotFoundException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
        }catch (BadInputException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.BAD_REQUEST);
        }catch (NoContentException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
