package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student getStudentById(Integer id);
}