package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


public class GetInternshipPositionOffersCommandCall implements APIControllerCommandCall<ResponseEntity<List<DTO>>> {

    private final InterviewManager interviewManager;
    private final String studentID;

    public GetInternshipPositionOffersCommandCall(InterviewManager interviewManager, String studentID) {
        this.interviewManager = interviewManager;
        this.studentID = studentID;
    }

    @Override
    public ResponseEntity<List<DTO>> execute() {
        try{
            List<InternshipPosOffer> internshipPosOffers = interviewManager.getInterviewPosOffersOfStudent(studentID);
            if(internshipPosOffers.isEmpty()){
                throw new NotFoundException("No internship position offers found for this student");
            }
            List<DTO> DTOIntPosOff = new ArrayList<>();
            for(InternshipPosOffer internshipPosOffer : internshipPosOffers){
                DTOIntPosOff.add(DTOCreator.createDTO(DTOTypes.INTERNSHIP_POS_OFFER, internshipPosOffer));
            }
            return new ResponseEntity<>(DTOIntPosOff, HttpStatus.OK);
        }catch (BadInputException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.BAD_REQUEST);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(List.of(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
