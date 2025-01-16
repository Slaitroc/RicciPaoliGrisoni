package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student getStudentsById(Integer id);
}