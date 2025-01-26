package click.studentandcompanies.entityManager.accountManager.accountManagerCommands.PUT;

import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entityManager.accountManager.AccountManagerCommand;
import click.studentandcompanies.entityRepository.AccountRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        UserType type = UserType.fromString((String) payload.get("userType"));
        validateByUserTypePayload(payload, type);
        Account Account = createAccount(payload, type);
        return accountRepository.save(Account);
    }

    private void validatePayload(Map<String, Object> payload) {
        if (payload.get("user_id") == null) {
            throw new BadInputException("user_id is required");
        }
        if(payload.get("userType") == null){
            throw new BadInputException("type is required");
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
        if (payload.get("country") == null) {
            throw new BadInputException("country is required");
        }
    }

    private void validateByUserTypePayload(Map<String, Object> payload, UserType type) {
        switch (type) {
            case STUDENT -> {
                if(payload.get("surname") == null){
                    throw new BadInputException("surname is required");
                }
                if(payload.get("uniVat") == null){
                    throw new BadInputException("the vat number of the student's university is required");
                }
                if(payload.get("birthDate") == null){
                    throw new BadInputException("birthDate is required");
                }
                try{
                    LocalDate birthDate = LocalDate.parse((String) payload.get("birthDate"));
                }catch (DateTimeParseException e){
                    throw new BadInputException("Invalid birthDate format");
                }
            }
            case COMPANY -> {
                if (payload.get("vatNumber") == null) {
                    throw new BadInputException("Vat number of company is required");
                }
            }
            case UNIVERSITY -> {
                if (payload.get("vatNumber") == null) {
                    throw new BadInputException("Vat number of university is required");
                }
                if(payload.get("uniDescription") == null){
                    throw new BadInputException("Description of the university is required");
                }
            }
            //Default case should only be UNKNOWN when the type is not recognized
            default -> throw new BadInputException("Invalid type");
        }
    }

    private Account createAccount(Map<String, Object> payload, UserType type) {
        //Mandatory fields
        String userId = (String) payload.get("user_id");
        String name = (String) payload.get("name");
        String email = (String) payload.get("email");
        String country = (String) payload.get("country");
        Boolean validated = false;
        //Optional fields
        String surname = (String) payload.get("surname") != null ? (String) payload.get("surname") : null;
        Integer uniVat = (Integer) payload.get("uniVat") != null ? (Integer) payload.get("uniVat") : null;
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse((String) payload.get("birthDate"));
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e); //the date is already validated, this exception should never be thrown
        }
        Integer vatNumber = (Integer) payload.get("vatNumber") != null ? (Integer) payload.get("vatNumber") : null;
        String uniDescription = (String) payload.get("uniDescription") != null ? (String) payload.get("uniDescription") : null;
        return new Account(userId, name, email, country, type, validated, surname, uniVat, birthDate, vatNumber, uniDescription);
    }

}
