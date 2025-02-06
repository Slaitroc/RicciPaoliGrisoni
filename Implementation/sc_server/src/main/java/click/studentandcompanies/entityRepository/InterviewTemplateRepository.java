package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.InterviewTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewTemplateRepository extends JpaRepository<InterviewTemplate, Integer> {
}