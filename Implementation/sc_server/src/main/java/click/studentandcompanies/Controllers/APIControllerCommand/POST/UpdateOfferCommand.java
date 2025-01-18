package click.studentandcompanies.Controllers.APIControllerCommand.POST;

import click.studentandcompanies.Controllers.APIControllerCommand.APIControllerCommand;
import click.studentandcompanies.DTO.DTO;
import click.studentandcompanies.DTO.DTOCreator;
import click.studentandcompanies.DTO.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.SubmissionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class UpdateOfferCommand implements APIControllerCommand<ResponseEntity<DTO>> {
    private final Map<String, Object> payload;
    private final SubmissionManager submissionManager;

    public UpdateOfferCommand(Map<String, Object> payload, SubmissionManager submissionManager) {
        this.payload = payload;
        this.submissionManager = submissionManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            InternshipOffer offer = submissionManager.updateInternshipOfferCall(payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, offer), HttpStatus.CREATED);
        }catch(IllegalArgumentException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (IllegalCallerException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (IllegalAccessException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
