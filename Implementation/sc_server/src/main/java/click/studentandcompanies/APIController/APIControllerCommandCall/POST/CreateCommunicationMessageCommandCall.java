package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.Message;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateCommunicationMessageCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {

    private final CommunicationManager communicationManager;
    private final String userID;
    private final Map<String, Object> payload;
    private final Integer commID;
    private final NotificationManager notificationManager;
    public CreateCommunicationMessageCommandCall(CommunicationManager communicationManager, String userID, Integer commID, Map<String, Object> payload, NotificationManager notificationManager) {
        this.userID = userID;
        this.communicationManager = communicationManager;
        this.payload = payload;
        this.commID = commID;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            Message message = communicationManager.createMessage(userID, commID, payload);
            DTO dto = DTOCreator.createDTO(DTOTypes.MESSAGE, message);

            NotificationData data = new NotificationData(NotificationTriggerType.MESSAGE_CREATED, dto);
            new NotificationController(notificationManager).sendAndSaveNotification(creteUserIDs(message.getCommunication()), data);
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

    private List<String> creteUserIDs(Communication communication){
        List<String> userIDs = new ArrayList<>();
        Recommendation recommendationOfCommunication = communication.getInternshipPosOff().getInterview().getRecommendation();
        SpontaneousApplication spontaneousApplicationOfCommunication = communication.getInternshipPosOff().getInterview().getSpontaneousApplication();
        if(recommendationOfCommunication != null) {
            userIDs.add(recommendationOfCommunication.getCv().getStudent().getId());
            userIDs.add(recommendationOfCommunication.getCv().getStudent().getUniversity().getId());
            userIDs.add(recommendationOfCommunication.getInternshipOffer().getCompany().getId());
        }else{
            userIDs.add(spontaneousApplicationOfCommunication.getStudent().getId());
            userIDs.add(spontaneousApplicationOfCommunication.getStudent().getUniversity().getId());
            userIDs.add(spontaneousApplicationOfCommunication.getInternshipOffer().getCompany().getId());
        }
        return userIDs;
    }
}
