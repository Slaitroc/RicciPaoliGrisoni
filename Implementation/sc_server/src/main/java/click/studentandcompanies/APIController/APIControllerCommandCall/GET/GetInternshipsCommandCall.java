package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetInternshipsCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {
    private final String userID;
    private final SubmissionManager submissionManager;

    public GetInternshipsCommandCall(String userID, SubmissionManager submissionManager) {
        this.userID = userID;
        this.submissionManager = submissionManager;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        if (userID == null) {
            throw new UnauthorizedException("User token not found");
        }
        try {
            List<InternshipOffer> internships = submissionManager.getAllInternships();
            if (internships.isEmpty()) {
                return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, "No internships found")), HttpStatus.NO_CONTENT);
            }
            List<DTO> internshipDTOs = new ArrayList<>();
            for (InternshipOffer internship : internships) {
                internshipDTOs.add(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, internship));
            }
            return new ResponseEntity<>(internshipDTOs, HttpStatus.OK);
        } catch (BadInputException e) {
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
        }catch (UnauthorizedException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
