package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {
}