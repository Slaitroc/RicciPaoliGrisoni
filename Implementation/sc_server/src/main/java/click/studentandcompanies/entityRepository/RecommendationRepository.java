package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
    Recommendation getRecommendationById(Integer id);

    @Query("SELECT r FROM Recommendation r WHERE r.cv.student.id = :studentId")
    List<Recommendation> findRecommendationByStudentId(@Param("studentId") String studentId);

    @Query("SELECT r FROM Recommendation r WHERE r.internshipOffer.company.id = :companyId")
    List<Recommendation> findRecommendationByCompanyId(@Param("companyId") String companyId);

    List<Recommendation> findRecommendationByInternshipOfferId(Integer internshipID);

    void removeRecommendationByInternshipOffer_Id(Integer internshipOfferId);
}