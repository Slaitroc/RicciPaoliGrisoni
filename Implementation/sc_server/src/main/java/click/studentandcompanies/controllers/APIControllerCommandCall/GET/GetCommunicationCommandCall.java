package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entityManager.CommunicationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetCommunicationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {

    Integer commID;
    CommunicationManager communicationManager;

    public GetCommunicationCommandCall(Integer commID, CommunicationManager communicationManager) {
        this.commID = commID;
        this.communicationManager = communicationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            DTO dto = DTOCreator.createDTO(DTOTypes.COMMUNICATION, communicationManager.getCommunication(commID));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
