package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CvRepository extends JpaRepository<Cv, Integer> {
    Cv getCvByStudent_Id(Integer studentId);
}