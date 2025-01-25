package click.studentandcompanies.APIController.APIControllerCommandCall.PUT;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entityManager.accountManager.AccountManager;
import click.studentandcompanies.utils.exception.BadInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class SendUserDataCommandCall implements APIControllerCommandCall<Object> {
    Map<String, Object> payload;
    AccountManager accountManager;
    public SendUserDataCommandCall(Map<String, Object> payload, AccountManager accountManager) {
        this.payload = payload;
        this.accountManager = accountManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            Account account = accountManager.sendUserData(payload);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ACCOUNT, account), HttpStatus.CREATED);
        }catch (BadInputException e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
