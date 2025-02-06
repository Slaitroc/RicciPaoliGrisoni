package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.InterviewQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuizRepository extends JpaRepository<InterviewQuiz, Integer> {
}