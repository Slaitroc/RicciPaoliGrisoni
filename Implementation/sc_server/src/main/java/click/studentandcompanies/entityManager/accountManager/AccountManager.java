package click.studentandcompanies.entityManager.accountManager;

import click.studentandcompanies.entity.Account;
import click.studentandcompanies.entity.University;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.accountManager.accountManagerCommands.POST.ConfirmUserCommand;
import click.studentandcompanies.entityManager.accountManager.accountManagerCommands.PUT.SendUserDataCommand;
import click.studentandcompanies.entityRepository.AccountRepository;
import click.studentandcompanies.entityRepository.CompanyRepository;
import click.studentandcompanies.entityRepository.StudentRepository;
import click.studentandcompanies.entityRepository.UniversityRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountManager {
    private final UserManager userManager;
    private final UniversityRepository universityRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    public AccountManager(UserManager userManager, UniversityRepository universityRepository, StudentRepository studentRepository,
                          CompanyRepository companyRepository, AccountRepository accountRepository) {
        this.userManager = userManager;
        this.universityRepository = universityRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
    }

    public Account sendUserData(Map<String, Object> payload) throws BadInputException {
        return new SendUserDataCommand(payload, accountRepository).execute();
    }

    public Account confirmUser(Map<String, Object> payload) throws BadInputException {
        return new ConfirmUserCommand(payload, accountRepository, studentRepository, companyRepository, universityRepository).execute();
    }

    public Map<String, Integer> getUniversitiesMap() {
        List<University> universities = universityRepository.findAll();
        return universities.stream().collect(
                java.util.stream.Collectors.toMap(University::getName, University::getVatNumber));
    }
}
