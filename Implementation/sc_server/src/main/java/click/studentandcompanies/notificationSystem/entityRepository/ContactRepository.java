package click.studentandcompanies.notificationSystem.entityRepository;

import click.studentandcompanies.notificationSystem.entity.Contact;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> getContactsByUserId(@Size(max = 255) @NotNull String userId);
}
