package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}