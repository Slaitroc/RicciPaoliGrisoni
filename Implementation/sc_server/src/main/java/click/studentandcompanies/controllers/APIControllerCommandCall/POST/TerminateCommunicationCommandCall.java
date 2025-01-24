package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.controllers.NotificationController;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class TerminateCommunicationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final CommunicationManager communicationManager;
    private final Map<String, Object> payload;
    private final int communicationID;
    private final NotificationManager notificationManager;

    public TerminateCommunicationCommandCall(CommunicationManager communicationManager, int communicationID, Map<String, Object> payload, NotificationManager notificationManager) {
        this.communicationManager = communicationManager;
        this.communicationID = communicationID;
        this.payload = payload;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Communication communication = communicationManager.terminateCommunication(communicationID, payload);

            List<Integer> userIDs = List.of(communication.getStudent().getId(), communication.getInternshipOffer().getCompany().getId(), communication.getUniversity().getId());
            DTO dto = DTOCreator.createDTO(DTOTypes.COMMUNICATION, communication);
            NotificationData data = new NotificationData(NotificationTriggerType.TERMINATE_COMMUNICATION, dto);
            new NotificationController(notificationManager).sendNotification(userIDs, data);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (BadInputException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}