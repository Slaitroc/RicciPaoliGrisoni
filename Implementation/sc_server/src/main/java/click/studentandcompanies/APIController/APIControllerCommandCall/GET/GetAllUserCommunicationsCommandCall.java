package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetAllUserCommunicationsCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {

    CommunicationManager communicationManager;
    String userID;

    public GetAllUserCommunicationsCommandCall(String userID, CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
        this.userID = userID;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        List<DTO> dtos = new ArrayList<>();
        try{
            List<Communication> communications = communicationManager.getAllUserCommunications(userID);
            //For every InternshipOffer in the list, create a DTO and add it to the list of DTOs
            for (Communication communication : communications) {
                dtos.add(DTOCreator.createDTO(DTOTypes.COMMUNICATION, communication));
            }
            //Return the list of DTOs with a status code of 200 (OK)
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (NotFoundException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
        }catch (NoContentException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
