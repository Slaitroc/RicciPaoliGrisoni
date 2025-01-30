package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Message;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetCommunicationMessagesCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {

    Integer commID;
    String userID;
    CommunicationManager communicationManager;

    public GetCommunicationMessagesCommandCall(Integer commID, String userID, CommunicationManager communicationManager) {
        this.commID = commID;
        this.userID = userID;
        this.communicationManager = communicationManager;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        try{
            List<DTO> dtos = new ArrayList<>();
            List<Message> messages = communicationManager.getCommunicationMessages(commID, userID);

            for (Message message : messages) {
                dtos.add(DTOCreator.createDTO(DTOTypes.MESSAGE, message));
            }

            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
        }catch (UnauthorizedException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
