package click.studentandcompanies.entityManager.accountManager.accountManagerCommands.POST;

import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entity.University;
import click.studentandcompanies.entityManager.accountManager.AccountManager;
import click.studentandcompanies.entityManager.accountManager.AccountManagerCommand;
import click.studentandcompanies.entityRepository.AccountRepository;
import click.studentandcompanies.entityRepository.CompanyRepository;
import click.studentandcompanies.entityRepository.StudentRepository;
import click.studentandcompanies.entityRepository.UniversityRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.Map;

public class ConfirmUserCommand implements AccountManagerCommand<Account> {
    private final Map<String, Object> payload;
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final UniversityRepository universityRepository;


    public ConfirmUserCommand(Map<String, Object> payload, AccountRepository accountRepository
            , StudentRepository studentRepository, CompanyRepository companyRepository, UniversityRepository universityRepository) {
        this.payload = payload;
        this.accountRepository = accountRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    public Account execute() throws BadInputException {
        validatePayload();
        Account account = accountRepository.findByUuid((String) payload.get("uuid"));
        if (account == null) {
            throw new NotFoundException("Account not found");
        }
        UserType userType = UserType.fromString((String) payload.get("userType"));
        createUser(userType, account);
        account.setValidate(true);
        return accountRepository.save(account);
    }

    private void createUser(UserType userType, Account account) {
        switch (userType) {
            case STUDENT:
                Student student = new Student();
                //todo id
                student.setName(account.getName());
                student.setEmail(account.getEmail());
                //The correctness of the university id is already check in the "sendUserData" command.
                // Therefore, the exception will not be thrown here.
                University enrolledUni = universityRepository.findById(account.getEnrolledInUniId()).orElseThrow(() ->
                        new NotFoundException("University not found"));
                student.setUniversity(enrolledUni);
                studentRepository.save(student);
                break;
            case COMPANY:
                Company company = new Company();
                //todo id
                company.setName(account.getName());
                company.setEmail(account.getEmail());
                company.setVatNumber(account.getVatNumber());
                company.setCountry(account.getCountry());
                companyRepository.save(company);
                break;
            case UNIVERSITY:
                University university = new University();
                //todo id
                university.setName(account.getName());
                university.setEmail(account.getEmail());
                university.setCountry(account.getCountry());
                universityRepository.save(university);
                break;
            default:
                break;
        }
    }


    private void validatePayload() throws BadInputException {
        if (payload.get("uuid") == null) {
            throw new BadInputException("UUID is required");
        }
        if (payload.get("userType") == null) {
            throw new BadInputException("User type is required");
        }
        UserType userType = UserType.fromString((String) payload.get("userType"));
        if (userType == UserType.UNKNOWN) {
            throw new BadInputException("User type is invalid");
        }
    }
}
