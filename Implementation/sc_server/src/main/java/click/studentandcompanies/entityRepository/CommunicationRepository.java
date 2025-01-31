package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {

}