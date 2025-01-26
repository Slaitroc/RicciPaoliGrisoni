package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class CloseInternshipOfferCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {

    Integer internshipID;
    Map<String, Object> payload;
    SubmissionManager submissionManager;
    UserManager userManager;
    NotificationManager notificationManager;
    RecommendationProcess recommendationProcess;

    public CloseInternshipOfferCommandCall(Integer internshipID, Map<String, Object> payload, SubmissionManager submissionManager,
                                           UserManager userManager, RecommendationProcess recommendationProcess, NotificationManager notificationManager) {
        this.internshipID = internshipID;
        this.payload = payload;
        this.submissionManager = submissionManager;
        this.userManager = userManager;
        this.notificationManager = notificationManager;
        this.recommendationProcess = recommendationProcess;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            // Retrieve the user IDs of the users involved in the internship (students in this case)
            List<String> userIDs = userManager.getInvolvedUsers(internshipID);
            // Close the internship offer and get the cancelled internship offer
            submissionManager.closeRelatedApplications(internshipID);
            recommendationProcess.closeRelatedRecommendations(internshipID);
            InternshipOffer internshipOffer = submissionManager.closeInternshipOffer(internshipID, payload);
            // Create a DTO object from the internship offer
            DTO dto = DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, internshipOffer);
            // Compose a notification data object with the Type of notification to send and the DTO object to include all the data that may be needed
            NotificationData data = new NotificationData(NotificationTriggerType.INTERNSHIP_CANCELLED, dto);
            // Facade with only one method to send the notification to the users to hide complexity and make it easier to use
            new NotificationController(notificationManager).sendNotification(userIDs, data);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
