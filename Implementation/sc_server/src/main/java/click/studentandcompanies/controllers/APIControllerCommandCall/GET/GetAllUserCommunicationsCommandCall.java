package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.CommunicationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetAllUserCommunicationsCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {

    CommunicationManager communicationManager;
    Integer userID;

    public GetAllUserCommunicationsCommandCall(Integer userID, CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        this.userID = userID;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        List<DTO> dtos = new ArrayList<>();
        try{
            List<Communication> communications = communicationManager.getAllUserCommunications(userID);
            if (communications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            //For every InternshipOffer in the list, create a DTO and add it to the list of DTOs
            for (Communication communication : communications) {
                dtos.add(DTOCreator.createDTO(DTOTypes.COMMUNICATION, communication));
            }
            //Return the list of DTOs with a status code of 200 (OK)
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
