package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.dbEnum.InternshipPosOfferStatusEnum;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetAcceptedInternshipPositionOffersCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {
    private InterviewManager interviewManager;
    private String userID;

    public GetAcceptedInternshipPositionOffersCommandCall(InterviewManager interviewManager, String userID) {
        this.interviewManager = interviewManager;
        this.userID = userID;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        try {
            List<InternshipPosOffer> internshipPosOffers = interviewManager.getInterviewPosOffersOfUser(userID).stream()
                    .filter(internshipPosOffer -> internshipPosOffer.getStatus()
                            .equals(InternshipPosOfferStatusEnum.accepted)).toList();
            List<DTO> DTOIntPosOff = new ArrayList<>();
            for (InternshipPosOffer internshipPosOffer : internshipPosOffers) {
                DTOIntPosOff.add(DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer));
            }
            return new ResponseEntity<>(DTOIntPosOff, HttpStatus.OK);
        } catch (
                NoContentException e) {
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NO_CONTENT);
        } catch (
                UnauthorizedException e) {
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
