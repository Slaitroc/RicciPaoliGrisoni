package click.studentandcompanies.entityManager.accountManager.accountManagerCommands.PUT;

import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entity.University;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.accountManager.AccountManagerCommand;
import click.studentandcompanies.entityRepository.AccountRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import org.apache.catalina.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SendUserDataCommand implements AccountManagerCommand<Account> {
    private final Map<String, Object> payload;
    private final AccountRepository accountRepository;
    private final UserManager userManager;
    public SendUserDataCommand(Map<String, Object> payload, AccountRepository AccountRepository, UserManager userManager) {
        this.payload = payload;
        this.accountRepository = AccountRepository;
        this.userManager = userManager;
    }

    @Override
    public Account execute() {
        UserType type = validatePayload(payload);
        validateByUserTypePayload(payload, type);
        Account Account = createAccount(payload, type);
        return accountRepository.save(Account);
    }

    private UserType validatePayload(Map<String, Object> payload) {
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
        UserType userType;
        try{
            userType = UserType.valueOf((String) payload.get("userType"));
        }catch (IllegalArgumentException e){
            throw new BadInputException("Invalid type");
        }
        return userType;
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
                List<University> uniList = userManager.getUniversity();
                uniList.stream().filter(uni -> uni.getVatNumber().equals((Integer) payload.get("uniVat"))).findFirst().orElseThrow(() -> new BadInputException("University not found"));
                if(payload.get("birthDate") == null){
                    throw new BadInputException("birthDate is required");
                }
                try{
                    Date birthDate = (Date) payload.get("birthDate");
                }catch (ClassCastException e){
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
        String uniVat = (String) payload.get("uniVat") != null ? (String) payload.get("uniVat") : null;
        Date birthDate = (Date) payload.get("birthDate") != null ? (Date) payload.get("birthDate") : null;
        Integer vatNumber = (Integer) payload.get("vatNumber") != null ? (Integer) payload.get("vatNumber") : null;
        String uniDescription = (String) payload.get("uniDescription") != null ? (String) payload.get("uniDescription") : null;
        return new Account(userId, name, email, country, type, validated, surname, uniVat, birthDate, vatNumber, uniDescription);
    }

}
