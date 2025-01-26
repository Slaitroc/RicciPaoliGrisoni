package click.studentandcompanies.notificationSystem.entityRepository;

import click.studentandcompanies.notificationSystem.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, String> {

    List<Contact> getAllById(String id);
}
