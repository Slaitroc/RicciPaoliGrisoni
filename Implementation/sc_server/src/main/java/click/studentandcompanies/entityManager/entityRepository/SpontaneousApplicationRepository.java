package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.SpontaneousApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpontaneousApplicationRepository extends JpaRepository<SpontaneousApplication, Integer> {
}