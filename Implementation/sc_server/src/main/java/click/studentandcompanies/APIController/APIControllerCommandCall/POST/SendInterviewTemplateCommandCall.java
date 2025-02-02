package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.notificationSystem.NotificationController;
import click.studentandcompanies.notificationSystem.NotificationManager;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class SendInterviewTemplateCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final int interviewID;
    private final int templateID;
    private final String companyID;
    private final InterviewManager interviewManager;
    private final NotificationManager notificationManager;

    public SendInterviewTemplateCommandCall(InterviewManager interviewManager, int interviewID, int templateID, String companyID, NotificationManager notificationManager) {
        this.interviewManager = interviewManager;
        this.interviewID = interviewID;
        this.templateID = templateID;
        this.companyID = companyID;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Interview interview = interviewManager.sendInterviewTemplate(interviewID, templateID, companyID);
            DTO dto = DTOCreator.createDTO(DTOTypes.INTERVIEW, interview);

            NotificationData data = new NotificationData(NotificationTriggerType.INTERVIEW_ANSWER_SENT, dto);
            new NotificationController(notificationManager).sendAndSaveNotification(List.of(companyID), data);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BadInputException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
