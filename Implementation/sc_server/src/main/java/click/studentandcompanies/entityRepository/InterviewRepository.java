package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {
    Interview getInterviewById(Integer id);
}