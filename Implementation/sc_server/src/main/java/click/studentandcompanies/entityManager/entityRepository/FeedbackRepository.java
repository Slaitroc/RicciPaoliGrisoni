package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}