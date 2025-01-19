package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetCompanyInternshipsCommandCall {
    SubmissionManager submissionManager;
    int companyID;

    public GetCompanyInternshipsCommandCall(SubmissionManager submissionManager, int companyID) {
        this.submissionManager = submissionManager;
        this.companyID = companyID;
    }

    public ResponseEntity<List<DTO>> execute() {
        List<DTO> dtos = new ArrayList<>();
        try{
            List<InternshipOffer> internshipOffers = submissionManager.getInternshipsByCompany(companyID);
            if (internshipOffers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
//For every InternshipOffer in the list, create a DTO and add it to the list of DTOs
            for (InternshipOffer offer : internshipOffers) {
                dtos.add(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, offer));
            }
//Return the list of DTOs with a status code of 200 (OK)
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}