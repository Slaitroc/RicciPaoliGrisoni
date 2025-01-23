package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> getAllById(Integer id);
}
