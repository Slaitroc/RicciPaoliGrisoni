package click.studentandcompanies.entityManager.entityRepository;
import click.studentandcompanies.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UniversityRepository extends JpaRepository<University, Integer> {

    University findByName(String name);

    long countUniversitiesByCountry(String country);

    @Query("SELECT COUNT(id) FROM University id")
    long countAll();

    University getUniversityById(Integer id);
}