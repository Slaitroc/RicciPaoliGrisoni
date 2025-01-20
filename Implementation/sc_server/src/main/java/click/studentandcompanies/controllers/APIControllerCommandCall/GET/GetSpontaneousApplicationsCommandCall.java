package click.studentandcompanies.controllers.APIControllerCommandCall.GET;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManager;
import click.studentandcompanies.utils.UserType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetSpontaneousApplicationsCommandCall {
    UserManager userManager;
    SubmissionManager submissionManager;
    Integer userID;

    public GetSpontaneousApplicationsCommandCall(UserManager userManager, SubmissionManager submissionManager, Integer userID) {
        this.userManager = userManager;
        this.submissionManager = submissionManager;
        this.userID = userID;
    }

    public ResponseEntity<List<DTO>>  execute() {
        List<DTO> dtos = new ArrayList<>();
        try {
            UserType type = userManager.getUserType(userID);
            List<SpontaneousApplication> applications;
            switch (type) {
                case COMPANY:
                    applications = submissionManager.getSpontaneousApplicationByCompany(userID);
                    break;
                case STUDENT:
                    applications = submissionManager.getSpontaneousApplicationByStudent(userID);
                    break;
                case UNIVERSITY:
                    dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, "User is not a company or student"));
                    return new ResponseEntity<>(dtos, HttpStatus.BAD_REQUEST);
                default:
                    dtos.add(DTOCreator.createDTO(DTOTypes.ERROR, "User not found"));
                    return new ResponseEntity<>(dtos, HttpStatus.BAD_REQUEST);
            }
            if (applications.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for(SpontaneousApplication application : applications){
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
