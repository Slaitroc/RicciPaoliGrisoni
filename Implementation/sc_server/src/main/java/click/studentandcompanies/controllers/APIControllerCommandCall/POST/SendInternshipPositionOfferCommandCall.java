package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.notificationSystem.NotificationFacade;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class SendInternshipPositionOfferCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final Map<String, Object> payload;
    private final InterviewManager interviewManager;
    private final Integer interviewID;
    private final NotificationManager notificationManager;
    private final UserManager userManager;

    public SendInternshipPositionOfferCommandCall(Integer interviewID, Map<String, Object> payload, InterviewManager interviewManager, UserManager userManager, NotificationManager notificationManager) {
        this.payload = payload;
        this.interviewManager = interviewManager;
        this.interviewID = interviewID;
        this.notificationManager = notificationManager;
        this.userManager = userManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            InternshipPosOffer internshipPosOffer = interviewManager.sendInterviewPositionOffer(interviewID, payload);

            DTO dto = DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer);
            NotificationData data = new NotificationData(NotificationTriggerType.INTERNSHIP_POSITION_OFFER_SENT, dto);
            List<Integer> studentID = List.of(userManager.getStudentIDByInternshipPosOfferID(internshipPosOffer.getId()));
            new NotificationFacade(notificationManager).sendNotification(studentID, data);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (BadInputException | WrongStateException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
