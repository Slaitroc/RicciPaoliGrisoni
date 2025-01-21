package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.InterviewTemplate;
import org.hibernate.sql.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewTemplateRepository extends JpaRepository<InterviewTemplate, Integer> {

    InterviewTemplate getInterviewTemplateById(Integer id);
}