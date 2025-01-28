package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetCompanyInternshipsCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {
    SubmissionManager submissionManager;
    String companyID;
    String userID;

    public GetCompanyInternshipsCommandCall(String companyID, String userID, SubmissionManager submissionManager) {
        this.submissionManager = submissionManager;
        this.companyID = companyID;
        this.userID = userID;
    }

    public ResponseEntity<List<DTO>> execute() {
        List<DTO> dtos = new ArrayList<>();
        try{
            if(userID == null){
                dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, "User is not logged in"));
                return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
            }
            List<InternshipOffer> internshipOffers = submissionManager.getInternshipsByCompany(companyID);
            //For every InternshipOffer in the list, create a DTO and add it to the list of DTOs
            for (InternshipOffer offer : internshipOffers) {
                dtos.add(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, offer));
            }
            //Return the list of DTOs with a status code of 200 (OK)
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (NotFoundException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.NOT_FOUND);
        }catch (NoContentException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}