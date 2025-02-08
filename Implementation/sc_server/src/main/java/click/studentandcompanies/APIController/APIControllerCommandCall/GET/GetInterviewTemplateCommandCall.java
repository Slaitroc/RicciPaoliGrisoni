package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetInterviewTemplateCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final InterviewManager interviewManager;
    private final Integer templateID;
    private final String userID;
    public GetInterviewTemplateCommandCall(InterviewManager interviewManager, Integer templateID, String userID) {
        this.interviewManager = interviewManager;
        this.templateID = templateID;
        this.userID = userID;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            InterviewTemplate interviewTemplate = interviewManager.getInterviewTemplate(templateID, userID);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERVIEW_TEMPLATE, interviewTemplate), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (BadInputException | UnauthorizedException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
