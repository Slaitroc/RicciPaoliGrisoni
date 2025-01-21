package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company getCompanyById(Integer id);
}