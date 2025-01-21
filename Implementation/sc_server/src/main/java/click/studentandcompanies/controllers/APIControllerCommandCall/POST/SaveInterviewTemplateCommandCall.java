package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class SaveInterviewTemplateCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final int interviewID;
    private final InterviewManager interviewManager;
    private final Map<String, Object> payload;

    public SaveInterviewTemplateCommandCall(Integer interviewID, InterviewManager interviewManager, Map<String, Object> payload) {
        this.interviewID = interviewID;
        this.interviewManager = interviewManager;
        this.payload = payload;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            InterviewTemplate interview = interviewManager.saveInterviewTemplate(interviewID, payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERVIEW_TEMPLATE, interview), HttpStatus.CREATED);
        } catch (BadInputException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
