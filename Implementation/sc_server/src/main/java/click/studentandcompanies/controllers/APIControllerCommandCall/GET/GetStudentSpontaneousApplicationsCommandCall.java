package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetStudentSpontaneousApplicationsCommandCall {
    SubmissionManager submissionManager;
    Integer studentID;

    public GetStudentSpontaneousApplicationsCommandCall(SubmissionManager submissionManager, Integer studentID) {
        this.submissionManager = submissionManager;
        this.studentID = studentID;
    }

    public ResponseEntity<List<DTO>> execute() {
        List<DTO> dtos = new ArrayList<>();
        try {
            List<SpontaneousApplication> applicationsByStudent = submissionManager.getSpontaneousApplicationByStudent(studentID);
            if (applicationsByStudent.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for(SpontaneousApplication application : applicationsByStudent){
                dtos.add(DTOCreator.createDTO(DTOTypes.SPONTANEOUS_APPLICATION, application));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()));
            return new ResponseEntity<>(dtos, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
