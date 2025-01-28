package click.studentandcompanies.entityManager.accountManager.accountManagerCommands.PUT;

import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.University;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.accountManager.AccountManagerCommand;
import click.studentandcompanies.entityRepository.AccountRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
                List<University> uniList = userManager.getUniversity();
                uniList.stream()
                        .filter(uni -> uni.getVatNumber().equals(payload.get("uniVat")))
                        .findFirst()
                        .orElseThrow(() -> new BadInputException("University not found"));
                if(payload.get("birthDate") == null){
                    throw new BadInputException("birthDate is required");
                }
                try{
                    LocalDate.parse((String) payload.get("birthDate"));
                }catch (DateTimeParseException e){
                    throw new BadInputException("Invalid birthDate format");
                }
            }
            case COMPANY -> {
                if (payload.get("vatNumber") == null) {
                    throw new BadInputException("Vat number of company is required");
                }
                Integer vatNumber = (Integer) payload.get("vatNumber");
                if(userManager.getCompany().stream().map(Company::getVatNumber).toList().contains(vatNumber)){
                    throw new BadInputException("Vat number for this Company is already in use");
                }
                if(payload.get("location") == null){
                    throw new BadInputException("Location of the company is required");
                }
            }
            case UNIVERSITY -> {
                if (payload.get("vatNumber") == null) {
                    throw new BadInputException("Vat number of university is required");
                }
                List<University> uniList = userManager.getUniversity();
                Integer vatNumber = (Integer) payload.get("vatNumber");
                if(uniList.stream().map(University::getVatNumber).toList().contains(vatNumber)){
                    throw new BadInputException("Vat number for this University is already in use");
                }
                if(uniList.stream().map(University::getName).toList().contains((String) payload.get("name"))){
                    throw new BadInputException("Name of the university is already in use");
                }
                if(payload.get("uniDescription") == null){
                    throw new BadInputException("Description of the university is required");
                }
                if(payload.get("location") == null){
                    throw new BadInputException("Location of the university is required");
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
        String surname = payload.get("surname") != null ? (String) payload.get("surname") : null;
        Integer uniVat = payload.get("uniVat") != null ? (Integer) payload.get("uniVat") : null;
        Integer vatNumber = payload.get("vatNumber") != null ? (Integer) payload.get("vatNumber") : null;
        String uniDescription = payload.get("uniDescription") != null ? (String) payload.get("uniDescription") : null;
        String location = payload.get("location") != null ? (String) payload.get("location") : null;
        //BirtDate required a special parsing, so it is handled separately
        if(type == UserType.STUDENT){
            LocalDate birthDate;
            try {
                birthDate = LocalDate.parse((String) payload.get("birthDate"));
            } catch (DateTimeParseException e) {
                throw new RuntimeException(e); //the date is already validated, this exception should never be thrown
            }
            return new Account(userId, name, email, country, type, validated, surname, uniVat, birthDate, vatNumber, uniDescription, location);
        }else{
            return new Account(userId, name, email, country, type, validated, surname, uniVat, null, vatNumber, uniDescription, location);
        }
    }

}
