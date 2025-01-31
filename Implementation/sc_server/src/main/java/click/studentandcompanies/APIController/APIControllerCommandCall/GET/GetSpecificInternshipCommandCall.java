package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetSpecificInternshipCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final Integer internshipID;
    private final SubmissionManager submissionManager;
    public GetSpecificInternshipCommandCall(Integer internshipID, SubmissionManager submissionManager) {
        this.internshipID = internshipID;
        this.submissionManager = submissionManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            InternshipOffer internshipOffer = submissionManager.getSpecificInternship(internshipID);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, internshipOffer), HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
