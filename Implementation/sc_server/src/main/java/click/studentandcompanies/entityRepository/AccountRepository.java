package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}