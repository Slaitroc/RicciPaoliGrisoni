package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
}