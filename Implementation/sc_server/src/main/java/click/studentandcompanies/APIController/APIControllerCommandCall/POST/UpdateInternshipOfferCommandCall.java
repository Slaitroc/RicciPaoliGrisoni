package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.recommendationProcess.RecommendationProcess;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class UpdateInternshipOfferCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final Map<String, Object> payload;
    private final SubmissionManager submissionManager;
    private final RecommendationProcess recommendationProcess;

    public UpdateInternshipOfferCommandCall(Map<String, Object> payload, SubmissionManager submissionManager, RecommendationProcess recommendationProcess) {
        this.payload = payload;
        this.submissionManager = submissionManager;
        this.recommendationProcess = recommendationProcess;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            InternshipOffer offer = submissionManager.updateInternshipOffer(payload);
            recommendationProcess.startRecommendationProcess(offer);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_OFFER, offer), HttpStatus.CREATED);
        } catch (BadInputException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}