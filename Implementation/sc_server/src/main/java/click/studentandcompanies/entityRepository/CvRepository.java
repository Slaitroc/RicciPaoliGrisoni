package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv, Integer> {
    Cv getCvByStudent_Id(String studentId);
}