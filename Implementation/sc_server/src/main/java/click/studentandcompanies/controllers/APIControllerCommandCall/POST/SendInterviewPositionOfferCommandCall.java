package click.studentandcompanies.controllers.APIControllerCommandCall.POST;

import click.studentandcompanies.controllers.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.WrongStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class SendInterviewPositionOfferCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final Map<String, Object> payload;
    private final InterviewManager interviewManager;
    private final Integer interviewID;

    public SendInterviewPositionOfferCommandCall(InterviewManager interviewManager, Integer interviewID, Map<String, Object> payload) {
        this.payload = payload;
        this.interviewManager = interviewManager;
        this.interviewID = interviewID;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try {
            InternshipPosOffer internshipPosOffer = interviewManager.sendInterviewPositionOffer(interviewID, payload);
            //todo: call the notification manager to send the notification to the student
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer), HttpStatus.CREATED);
        } catch (BadInputException | WrongStateException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
