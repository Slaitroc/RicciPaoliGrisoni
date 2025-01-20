package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.SpontaneousApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpontaneousApplicationRepository extends JpaRepository<SpontaneousApplication, Integer> {
    List<SpontaneousApplication> getSpontaneousApplicationByStudent_Id(Integer studentId);

    @Query ("SELECT s FROM SpontaneousApplication s WHERE s.internshipOffer.company.id = :userID")
    List<SpontaneousApplication> findSpontaneousApplicationByCompanyId(@Param("userID") Integer userID);
}
