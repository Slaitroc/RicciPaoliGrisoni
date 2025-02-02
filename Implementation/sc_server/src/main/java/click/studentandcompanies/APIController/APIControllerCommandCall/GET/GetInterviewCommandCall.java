package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.Config;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetInterviewCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final String userID;
    private final Integer interviewID;
    private final InterviewManager interviewManager;

    public GetInterviewCommandCall(String userID, Integer interviewID, InterviewManager interviewManager) {
        this.userID = userID;
        this.interviewID = interviewID;
        this.interviewManager = interviewManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Interview interview = interviewManager.getSpecificInterview(interviewID, userID);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERVIEW, interview), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>((DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>((DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            Config.printStackTrace(e);
            return new ResponseEntity<>((DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
