package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.Config;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateCommunicationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final CommunicationManager communicationManager;
    private final Map<String, Object> payload;
    private final NotificationManager notificationManager;

    public CreateCommunicationCommandCall(CommunicationManager communicationManager, NotificationManager notificationManager, Map<String, Object> payload) {
        this.communicationManager = communicationManager;
        this.payload = payload;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Communication communication = communicationManager.createCommunication(payload);
            List<String> userIDs = createUserIDs(communication);

            DTO dto = DTOCreator.createDTO(DTOTypes.COMMUNICATION, communication);
            NotificationData data = new NotificationData(NotificationTriggerType.NEW_COMMUNICATION, dto);
            new NotificationController(notificationManager).sendAndSaveNotification(userIDs, data);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (BadInputException | WrongStateException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Config.printStackTrace(e);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<String> createUserIDs(Communication communication) {
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
