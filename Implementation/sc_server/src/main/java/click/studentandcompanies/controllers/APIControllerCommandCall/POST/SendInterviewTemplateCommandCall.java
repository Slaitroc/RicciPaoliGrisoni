package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class SendInterviewTemplateCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final int interviewID;
    private final int templateID;
    private final Map<String, Object> payload;
    private final InterviewManager interviewManager;

    public SendInterviewTemplateCommandCall(InterviewManager interviewManager, int interviewID, int templateID, Map<String, Object> payload) {
        this.interviewManager = interviewManager;
        this.interviewID = interviewID;
        this.templateID = templateID;
        this.payload = payload;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Interview interview = interviewManager.sendInterviewTemplate(interviewID, templateID, payload);
            //todo call the notification manager to inform the student
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERVIEW, interview), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BadInputException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch(UnauthorizedException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
