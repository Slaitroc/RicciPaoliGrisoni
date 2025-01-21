package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class CreateCommunicationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final CommunicationManager communicationManager;
    private final Map<String, Object> payload;

    public CreateCommunicationCommandCall(CommunicationManager communicationManager, Map<String, Object> payload) {
        this.communicationManager = communicationManager;
        this.payload = payload;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Communication communication = communicationManager.createCommunication(payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.COMMUNICATION, communication), HttpStatus.CREATED);
        } catch (BadInputException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
