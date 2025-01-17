package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.SpontaneousApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpontaneousApplicationRepository extends JpaRepository<SpontaneousApplication, Integer> {
    List<SpontaneousApplication> getSpontaneousApplicationByStudent_Id(Integer studentId);
}