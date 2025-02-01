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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class SendInterviewCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final int interviewID;
    private final Map<String, Object> payload;
    private final InterviewManager interviewManager;
    private final NotificationManager notificationManager;

    public SendInterviewCommandCall(int interviewID, Map<String, Object> payload, InterviewManager interviewManager, NotificationManager notificationManager) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.interviewManager = interviewManager;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Interview interview = interviewManager.sendInterview(interviewID, payload);
            DTO dto = DTOCreator.createDTO(DTOTypes.INTERVIEW, interview);
            NotificationData notificationData = new NotificationData(NotificationTriggerType.INTERVIEW_ASSIGNED, dto);
            String studentID;
            if(interview.getSpontaneousApplication() != null) {
                studentID = interview.getSpontaneousApplication().getStudent().getId();
            } else {
                studentID = interview.getRecommendation().getCv().getStudent().getId();
            }
            new NotificationController(notificationManager).sendAndSaveNotification(List.of(studentID), notificationData);
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
