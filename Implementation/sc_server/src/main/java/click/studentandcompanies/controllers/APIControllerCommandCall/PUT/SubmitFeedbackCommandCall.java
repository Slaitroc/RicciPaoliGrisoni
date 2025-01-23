package click.studentandcompanies.controllers.APIControllerCommandCall.PUT;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Feedback;
import click.studentandcompanies.entity.Recommendation;
import click.studentandcompanies.entityManager.feedbackMechanism.FeedbackMechanism;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class SubmitFeedbackCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>>{
    private final Map<String, Object> payload;
    private final FeedbackMechanism feedbackMechanism;
    private final Integer recommendationID;

    public SubmitFeedbackCommandCall(Integer recommendationID, Map<String, Object> payload, FeedbackMechanism feedbackMechanism) {
        this.recommendationID = recommendationID;
        this.payload = payload;
        this.feedbackMechanism = feedbackMechanism;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            Feedback feedback = feedbackMechanism.submitFeedback(recommendationID, payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.FEEDBACK, feedback), HttpStatus.CREATED);
        } catch (BadInputException | WrongStateException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
