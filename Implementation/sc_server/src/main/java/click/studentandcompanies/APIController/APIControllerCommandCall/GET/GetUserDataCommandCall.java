package click.studentandcompanies.APIController.APIControllerCommandCall.GET;

import click.studentandcompanies.APIController.APIControllerCommandCall.APIControllerCommandCall;
import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.dto.DTOCreator;
import click.studentandcompanies.dto.DTOTypes;
import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entityManager.accountManager.AccountManager;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetUserDataCommandCall implements APIControllerCommandCall<ResponseEntity<DTO>> {
    private final String userID;
    private final AccountManager accountManager;

    public GetUserDataCommandCall(String userID, AccountManager accountManager) {
        this.userID = userID;
        this.accountManager = accountManager;
    }

    @Override
    public ResponseEntity<DTO> execute() {
        try{
            Account account = accountManager.getAccountBy(userID);
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ACCOUNT, account), HttpStatus.OK);
        }catch (NotFoundException e) {
            //it is correct, the front-end will handle this, we need to send a response with no content (204)
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(DTOCreator.createDTO(DTOTypes.ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
