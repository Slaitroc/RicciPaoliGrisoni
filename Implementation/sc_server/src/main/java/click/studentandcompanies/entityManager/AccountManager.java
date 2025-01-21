package click.studentandcompanies.entityManager;

import click.studentandcompanies.entityRepository.CompanyRepository;
import click.studentandcompanies.entityRepository.StudentRepository;
import click.studentandcompanies.entityRepository.UniversityRepository;

public class AccountManager {
    private final UserManager userManager;
    private final UniversityRepository universityRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    public AccountManager(UserManager userManager, UniversityRepository universityRepository, StudentRepository studentRepository, CompanyRepository companyRepository) {
        this.userManager = userManager;
        this.universityRepository = universityRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
    }
}
