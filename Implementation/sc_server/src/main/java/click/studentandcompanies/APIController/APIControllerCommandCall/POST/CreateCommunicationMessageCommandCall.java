package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Message;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class CreateCommunicationMessageCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {

    private final CommunicationManager communicationManager;
    private final String userID;
    private final Map<String, Object> payload;
    private final Integer commID;

    public CreateCommunicationMessageCommandCall(CommunicationManager communicationManager, String userID, Integer commID, Map<String, Object> payload) {
        this.userID = userID;
        this.communicationManager = communicationManager;
        this.payload = payload;
        this.commID = commID;

    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            Message message = communicationManager.createMessage(userID, commID, payload);
            DTO dto = DTOCreator.createDTO(DTOTypes.MESSAGE, message);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }catch (NotFoundException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (UnauthorizedException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }catch (BadInputException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
