package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetInterviewTemplatesCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {

    private final InterviewManager interviewManager;
    private final String companyId;

    public GetInterviewTemplatesCommandCall(InterviewManager interviewManager, String companyId) {
        this.interviewManager = interviewManager;
        this.companyId = companyId;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        try{
            List<InterviewTemplate> interviewTemplates = interviewManager.getInterviewTemplates(companyId);
            if(interviewTemplates.isEmpty()){
                throw new NotFoundException("No interview templates found for this company");
            }
            List<DTO> interviewTemplateDTOs = new ArrayList<>();
            for(InterviewTemplate interviewTemplate : interviewTemplates){
                interviewTemplateDTOs.add(DTOCreator.createDTO(DTOTypes.INTERVIEW_TEMPLATE, interviewTemplate));
            }
            return new ResponseEntity<>(interviewTemplateDTOs, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
        }catch (BadInputException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
