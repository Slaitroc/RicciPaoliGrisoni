package click.studentandcompanies.entityManager.entityRepository;


import click.studentandcompanies.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    University findByName(String name);

    @Query("SELECT COUNT(u) FROM University u WHERE u.country = :country")
    long countUniversitiesByCountry(@Param("country") String country);

    @Query("SELECT COUNT(u) FROM University u")
    long countAllUniversity();
}