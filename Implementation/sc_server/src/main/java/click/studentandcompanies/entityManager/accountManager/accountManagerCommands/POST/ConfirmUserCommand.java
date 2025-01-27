package click.studentandcompanies.entityManager.accountManager.accountManagerCommands.POST;

import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entity.University;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.accountManager.AccountManagerCommand;
import click.studentandcompanies.entityRepository.AccountRepository;
import click.studentandcompanies.entityRepository.CompanyRepository;
import click.studentandcompanies.entityRepository.StudentRepository;
import click.studentandcompanies.entityRepository.UniversityRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.time.LocalDate;

import static click.studentandcompanies.utils.UserType.*;

public class ConfirmUserCommand implements AccountManagerCommand<Account> {
    private final String user_id;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final UniversityRepository universityRepository;
    private final AccountRepository accountRepository;

    public ConfirmUserCommand(String user_id, StudentRepository studentRepository, CompanyRepository companyRepository, UniversityRepository universityRepository, AccountRepository accountRepository) {
        this.user_id = user_id;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.universityRepository = universityRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Account execute() {
        Account account = accountRepository.findById(user_id).orElseThrow(() -> new NotFoundException("User not found"));
        if(account.getValidate()){
            return account;
        }
        switch (UserType.fromString(account.getUserType())) {
            case STUDENT:
                Student student = buildStudent(account);
                studentRepository.save(student);
                break;
            case COMPANY:
                Company company = buildCompany(account);
                companyRepository.save(company);
                break;
            case UNIVERSITY:
                University university = buildUniversity(account);
                universityRepository.save(university);
                break;
        }
        account.setValidate(true);
        return accountRepository.save(account);
    }

    private Student buildStudent(Account account) {
        String name = account.getName();
        String surname = account.getSurname();
        String email = account.getEmail();
        LocalDate birthDate = account.getBirthDate();
        String country = account.getCountry();
        University university = universityRepository.findByVatNumber(account.getUniVat());
        return new Student(user_id, name, email, university, surname, birthDate, country);
    }

    private Company buildCompany(Account account) {
        String name = account.getName();
        String email = account.getEmail();
        String country = account.getCountry();
        Integer vatNumber = account.getVatNumber();
        String location = account.getLocation();
        return new Company(user_id, name, email, country, vatNumber, location);
    }

    private University buildUniversity(Account account) {
        String name = account.getName();
        String email = account.getEmail();
        String country = account.getCountry();
        Integer vatNumber = account.getVatNumber();
        String uniDescription = account.getUniDesc();
        String location = account.getLocation();
        return new University(user_id, name, email, country, vatNumber, uniDescription, location);
    }
}
