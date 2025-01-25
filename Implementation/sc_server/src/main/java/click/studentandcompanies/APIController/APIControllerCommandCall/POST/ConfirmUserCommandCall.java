package click.studentandcompanies.APIController.APIControllerCommandCall.POST;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entityManager.accountManager.AccountManager;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ConfirmUserCommandCall implements APIControllerCommandCall<Object> {
    private final Map<String, Object> payload;
    private final AccountManager accountManager;
    public ConfirmUserCommandCall(Map<String, Object> payload, AccountManager accountManager) {
        this.payload = payload;
        this.accountManager = accountManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            Account account = accountManager.confirmUser(payload);
            return new ResponseEntity<DTO>(DTOCreator.createDTO(DTOTypes.ACCOUNT, account), HttpStatus.CREATED);
        }catch (BadInputException e){
            return new ResponseEntity<DTO>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e) {
            return new ResponseEntity<DTO>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<DTO>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
