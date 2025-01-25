package click.studentandcompanies.entityManager.accountManager.accountManagerCommands.PUT;

import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entityManager.accountManager.AccountManagerCommand;
import click.studentandcompanies.entityRepository.AccountRepository;
import click.studentandcompanies.utils.exception.BadInputException;

import java.util.List;
import java.util.Map;

public class SendUserDataCommand implements AccountManagerCommand<Account> {
    private final Map<String, Object> payload;
    private final AccountRepository accountRepository;

    public SendUserDataCommand(Map<String, Object> payload, AccountRepository AccountRepository) {
        this.payload = payload;
        this.accountRepository = AccountRepository;
    }

    @Override
    public Account execute() {
        validatePayload(payload);
        //todo validate based on the type of Account
        Account Account = createAccount(payload);
        return accountRepository.save(Account);
    }

    private void validatePayload(Map<String, Object> payload) {
        if (payload.get("uuid") == null) {
            throw new BadInputException("uuid is required");
        }
        if (payload.get("name") == null) {
            throw new BadInputException("name is required");
        }
        if (payload.get("email") == null) {
            throw new BadInputException("email is required");
        }
        List<String> emails = accountRepository.findAll().stream().map(Account::getEmail).toList();
        if (emails.contains((String) payload.get("email"))) {
            throw new BadInputException("email is already in use");
        }
    }

    private Account createAccount(Map<String, Object> payload) {
        String uuid = (String) payload.get("uuid");
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        Integer enrolled_in_uni_id = (Integer) payload.get("enrolled_in_uni_id") != null ? (Integer) payload.get("enrolled_in_uni_id") : null;
        Integer vatNumber = (Integer) payload.get("vatNumber") != null ? (Integer) payload.get("vatNumber") : null;
        String country = (String) payload.get("country") != null ? (String) payload.get("country") : null;
        Boolean validate = false;
        return new Account(uuid, name, email, enrolled_in_uni_id, vatNumber, country, validate);
    }

}
