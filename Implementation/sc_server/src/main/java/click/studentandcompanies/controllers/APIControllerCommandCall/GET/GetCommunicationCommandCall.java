package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetCommunicationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {

    Integer commID;
    Integer userID;
    CommunicationManager communicationManager;

    public GetCommunicationCommandCall(Integer commID, Integer userID, CommunicationManager communicationManager) {
        this.commID = commID;
        this.userID = userID;
        this.communicationManager = communicationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            DTO dto = DTOCreator.createDTO(DTOTypes.COMMUNICATION, communicationManager.getCommunication(commID, userID));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (UnauthorizedException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
