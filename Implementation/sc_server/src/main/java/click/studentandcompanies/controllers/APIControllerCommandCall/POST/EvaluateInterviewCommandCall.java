package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.NotificationManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.notificationSystem.NotificationFacade;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationData;
import click.studentandcompanies.notificationSystem.notificationUtils.NotificationTriggerType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EvaluateInterviewCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final InterviewManager interviewManager;
    private final int interviewId;
    private final Map<String, Object> payload;
    private final NotificationManager notificationManager;

    public EvaluateInterviewCommandCall(InterviewManager interviewManager, NotificationManager notificationManager, int interviewId, Map<String, Object> payload) {
        this.interviewManager = interviewManager;
        this.interviewId = interviewId;
        this.payload = payload;
        this.notificationManager = notificationManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Interview interview = interviewManager.evaluateInterview(interviewId, payload);

            List<Integer> userIDs = new ArrayList<>();
            if(interview.getSpontaneousApplication() != null)
                userIDs.add(interview.getRecommendation().getCv().getStudent().getId());
            else userIDs.add(interview.getSpontaneousApplication().getStudent().getId());

            DTO dto = DTOCreator.createDTO(DTOTypes.INTERVIEW, interview);
            NotificationData data = new NotificationData(NotificationTriggerType.INTERVIEW_EVALUATED, dto);
            new NotificationFacade(notificationManager).sendNotification(userIDs, data);

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
