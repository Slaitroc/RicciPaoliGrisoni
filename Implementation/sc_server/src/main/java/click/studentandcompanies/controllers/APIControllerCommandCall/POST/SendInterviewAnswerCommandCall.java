package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public class SendInterviewAnswerCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    int interviewID;
    Map<String, Object> payload;
    InterviewManager interviewManager;

    public SendInterviewAnswerCommandCall(int interviewID, Map<String, Object> payload, InterviewManager interviewManager) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.interviewManager = interviewManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            Interview interview = interviewManager.sendInterviewAnswer(interviewID, payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERVIEW, interview), HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (IllegalCallerException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }

    }
}
