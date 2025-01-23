package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Feedback;
import click.studentandcompanies.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findByRecommendation(Recommendation recommendation);
}