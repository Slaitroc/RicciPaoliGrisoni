package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.NoContentException;
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
        List<DTO> dtos = new ArrayList<>();
        try{
            if (companyId == null) {
                dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, "User is not logged in"));
                return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
            }

            List<InterviewTemplate> templates = interviewManager.getTemplates(companyId);

            for (InterviewTemplate template : templates) {
                dtos.add(DTOCreator.createDTO(DTOTypes.INTERVIEW_TEMPLATE, template));
            }

            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (NoContentException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
