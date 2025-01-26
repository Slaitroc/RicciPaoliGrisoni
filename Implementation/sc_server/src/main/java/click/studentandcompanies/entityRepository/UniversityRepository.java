package click.studentandcompanies.entityRepository;
import click.studentandcompanies.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, String> {

    University findByName(String name);

    long countUniversitiesByCountry(String country);

    @Query("SELECT COUNT(id) FROM University id")
    long countAll();

    University getUniversityById(String id);

    University findByVatNumber(Integer vatNumber);
}