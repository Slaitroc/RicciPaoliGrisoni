package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class SubmitSpontaneousApplicationCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final Map<String, Object> payload;
    private final SubmissionManager submissionManager;
    private final int internshipOfferID;
    private NotificationManager notificationManager;

    public SubmitSpontaneousApplicationCommandCall(int internshipOfferID, Map<String, Object> payload, SubmissionManager submissionManager, NotificationManager notificationManager) {
        this.payload = payload;
        this.submissionManager = submissionManager;
        this.internshipOfferID = internshipOfferID;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            SpontaneousApplication application = submissionManager.submitSpontaneousApplication(payload, internshipOfferID);

            DTO dto = DTOCreator.createDTO(DTOTypes.SPONTANEOUS_APPLICATION, application);
            NotificationData data = new NotificationData(NotificationTriggerType.SPONTANEOUS_APPLICATION_RECEIVED, dto);
            new NotificationController(notificationManager).sendNotification(List.of(application.getInternshipOffer().getCompany().getId()), data);

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

