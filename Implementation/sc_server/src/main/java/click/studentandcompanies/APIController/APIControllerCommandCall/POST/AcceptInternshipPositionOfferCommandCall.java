package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class AcceptInternshipPositionOfferCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {

    private final InterviewManager interviewManager;
    private final Integer intPosOffID;
    private final Map<String, Object> payload;

    public AcceptInternshipPositionOfferCommandCall(Integer intPosOffID, Map<String, Object> payload, InterviewManager interviewManager) {
        this.intPosOffID = intPosOffID;
        this.payload = payload;
        this.interviewManager = interviewManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            InternshipPosOffer internshipPosOffer = interviewManager.acceptInternshipPositionOffer(intPosOffID, payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (BadInputException | WrongStateException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
