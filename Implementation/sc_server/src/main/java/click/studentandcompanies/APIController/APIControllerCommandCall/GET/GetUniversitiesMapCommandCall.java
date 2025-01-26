package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entityManager.accountManager.AccountManager;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class GetUniversitiesMapCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final AccountManager accountManager;

    public GetUniversitiesMapCommandCall(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public ResponseEntity<DTO> execute() {
        try {
            Map<String, Integer> universities = accountManager.getUniversitiesMap();
            if (universities.isEmpty()) {
                throw new NotFoundException("No universities found");
            }
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.UNIVERSITY_MAP, universities), HttpStatus.OK);
        }catch (NotFoundException e) {
            // 204 No Content has not body, the response is empty and the error will not be shown to the front end
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
