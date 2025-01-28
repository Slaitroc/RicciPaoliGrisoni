package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class AcceptSpontaneousApplicationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {

    private final Integer spontaneousApplicationID;
    private final SubmissionManager submissionManager;
    private final NotificationManager notificationManager;
    private final Map<String, Object> payload;

    public AcceptSpontaneousApplicationCommandCall(Integer spontaneousApplicationID, SubmissionManager submissionManager, NotificationManager notificationManager, Map<String, Object> payload) {
        this.spontaneousApplicationID = spontaneousApplicationID;
        this.submissionManager = submissionManager;
        this.notificationManager = notificationManager;
        this.payload = payload;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            SpontaneousApplication application = submissionManager.acceptSpontaneousApplication(spontaneousApplicationID, payload);

            List<String> userIDs = List.of(application.getStudent().getId());

            NotificationData data = new NotificationData(NotificationTriggerType.SPONTANEOUS_APPLICATION_ACCEPTED, DTOCreator.createDTO(DTOTypes.SPONTANEOUS_APPLICATION, application));

            new NotificationController(notificationManager).sendAndSaveNotification(userIDs, data);

            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.SPONTANEOUS_APPLICATION, application), HttpStatus.CREATED);
        } catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
