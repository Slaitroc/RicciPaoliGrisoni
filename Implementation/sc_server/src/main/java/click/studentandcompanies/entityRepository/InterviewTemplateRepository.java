package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.InterviewTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewTemplateRepository extends JpaRepository<InterviewTemplate, Integer> {

    InterviewTemplate getInterviewTemplateById(Integer id);

    List<InterviewTemplate> getInterviewTemplateByCompany_Id(String companyId);
}