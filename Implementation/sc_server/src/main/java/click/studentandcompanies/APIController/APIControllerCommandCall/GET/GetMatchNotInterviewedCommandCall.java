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

public class GetMatchNotInterviewedCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {
    private final InterviewManager interviewManager;
    private final String companyID;

    public GetMatchNotInterviewedCommandCall(InterviewManager interviewManager, String companyID) {
        this.interviewManager = interviewManager;
        this.companyID = companyID;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        try{
            List<Interview> interviews = interviewManager.getMatchNotInterviewed(companyID);
            if(interviews.isEmpty()){
                throw new NotFoundException("No match found");
            }
            List<DTO> dtos = new ArrayList<>();
            for(Interview interview : interviews){
                dtos.add(DTOCreator.createDTO(DTOTypes.INTERVIEW, interview));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (BadInputException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
