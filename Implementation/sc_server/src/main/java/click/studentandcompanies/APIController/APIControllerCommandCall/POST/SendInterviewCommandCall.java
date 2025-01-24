package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class SendInterviewCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    int interviewID;
    Map<String, Object> payload;
    InterviewManager interviewManager;

    public SendInterviewCommandCall(int interviewID, Map<String, Object> payload, InterviewManager interviewManager) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.interviewManager = interviewManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Interview interview = interviewManager.sendInterview(interviewID, payload);
            //todo: call the notification manager to send the notification to the student
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERVIEW, interview), HttpStatus.CREATED);
        } catch (BadInputException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
