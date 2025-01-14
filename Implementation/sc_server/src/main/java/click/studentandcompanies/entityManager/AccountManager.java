package click.studentandcompanies.entityManager;

import click.studentandcompanies.entityManager.entityRepository.CompanyRepository;
import click.studentandcompanies.entityManager.entityRepository.StudentRepository;
import click.studentandcompanies.entityManager.entityRepository.UniversityRepository;

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
