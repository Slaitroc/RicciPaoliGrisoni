package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Account;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUuid(@Size(max = 255) String uuid);
}