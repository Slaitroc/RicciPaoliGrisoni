package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class RejectInternshipPositionOfferCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final InterviewManager interviewManager;
    private final NotificationManager notificationManager;
    private final UserManager userManager;
    private final Integer intPosOffID;
    private final Map<String, Object> payload;

    public RejectInternshipPositionOfferCommandCall(Integer intPosOffID, Map<String, Object> payload, InterviewManager interviewManager, NotificationManager notificationManager, UserManager userManager) {
        this.intPosOffID = intPosOffID;
        this.payload = payload;
        this.interviewManager = interviewManager;
        this.notificationManager = notificationManager;
        this.userManager = userManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            InternshipPosOffer internshipPosOffer = interviewManager.rejectInternshipPositionOffer(intPosOffID, payload);
            List<String> userIDs = List.of(userManager.getCompanyIDByInternshipPosOfferID(internshipPosOffer.getId()));
            DTO dto = DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer);
            NotificationData data = new NotificationData(NotificationTriggerType.INTERNSHIP_POSITION_OFFER_REJECTED , dto);
            new NotificationController(notificationManager).sendAndSaveNotification(userIDs, data);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BadInputException | WrongStateException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
