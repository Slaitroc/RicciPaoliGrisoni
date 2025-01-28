package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

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

import java.util.ArrayList;
import java.util.List;

public class GetInterviewsCommandCall implements APIControllerCommandCall <ResponseEntity<List<DTO>>> {
    private final String userID;
    private final InterviewManager interviewManager;

    public GetInterviewsCommandCall(String userID, InterviewManager interviewManager) {
        this.userID = userID;
        this.interviewManager = interviewManager;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        if(userID == null){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, "User ID is null")), HttpStatus.BAD_REQUEST);
        }
        try{
            List<Interview> interviews = interviewManager.getInterview(userID);
            if(interviews.isEmpty()){
                throw new NotFoundException("No interviews found for this user");
            }
            List<DTO> interviewDTOs = new ArrayList<>();
            for(Interview interview : interviews){
                interviewDTOs.add(DTOCreator.createDTO(DTOTypes.INTERVIEW, interview));
            }
            return new ResponseEntity<>(interviewDTOs, HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
        }catch (BadInputException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
