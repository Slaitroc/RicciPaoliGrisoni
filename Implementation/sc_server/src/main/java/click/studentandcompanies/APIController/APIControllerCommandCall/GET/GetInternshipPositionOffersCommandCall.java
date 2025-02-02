package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


public class GetInternshipPositionOffersCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {

    private final InterviewManager interviewManager;
    private final String userID;

    public GetInternshipPositionOffersCommandCall(InterviewManager interviewManager, String userID) {
        this.interviewManager = interviewManager;
        this.userID = userID;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        try{
            List<InternshipPosOffer> internshipPosOffers = interviewManager.getInterviewPosOffersOfUser(userID);
            List<DTO> DTOIntPosOff = new ArrayList<>();
            for(InternshipPosOffer internshipPosOffer : internshipPosOffers){
                DTOIntPosOff.add(DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer));
            }
            return new ResponseEntity<>(DTOIntPosOff, HttpStatus.OK);
        }catch (NoContentException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NO_CONTENT);
        }catch (UnauthorizedException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
