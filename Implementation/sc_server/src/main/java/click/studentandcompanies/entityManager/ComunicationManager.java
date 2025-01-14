package click.studentandcompanies.entityManager;

import click.studentandcompanies.entityManager.entityRepository.CompanyRepository;

public class ComunicationManager {
    private final UserManager userManager;
    private final CompanyRepository companyRepository;

    public ComunicationManager(UserManager userManager, CompanyRepository companyRepository) {
        this.userManager = userManager;
        this.companyRepository = companyRepository;
    }
}
