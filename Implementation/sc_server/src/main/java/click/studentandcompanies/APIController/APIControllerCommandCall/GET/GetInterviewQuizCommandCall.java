package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.InterviewQuiz;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetInterviewQuizCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final InterviewManager interviewManager;
    private final Integer interviewId;
    private final String companyID;

    public GetInterviewQuizCommandCall(InterviewManager interviewManager, Integer interviewId, String companyID) {
        this.interviewManager = interviewManager;
        this.interviewId = interviewId;
        this.companyID = companyID;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            InterviewQuiz interviewQuiz = interviewManager.getInterviewQuiz(interviewId, companyID);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.INTERVIEW_QUIZ, interviewQuiz), HttpStatus.OK);
        }catch(NotFoundException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch(BadInputException | UnauthorizedException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
