package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.SpontaneousApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpontaneousApplicationRepository extends JpaRepository<SpontaneousApplication, Integer> {

    @Query ("SELECT a FROM SpontaneousApplication a WHERE a.student.id = :userID")
    List<SpontaneousApplication> findSpontaneousApplicationByStudentId(@Param("userID") String userID);

    @Query ("SELECT s FROM SpontaneousApplication s WHERE s.internshipOffer.company.id = :userID")
    List<SpontaneousApplication> findSpontaneousApplicationByCompanyId(@Param("userID") String userID);

    List<SpontaneousApplication> getSpontaneousApplicationByStudent_IdAndInternshipOffer_Id(String studentId, Integer internshipOfferId);

    List<SpontaneousApplication> findAllByInternshipOfferId(Integer internshipID);

    void removeSpontaneousApplicationByInternshipOffer_Id(Integer internshipOfferId);

    SpontaneousApplication getSpontaneousApplicationById(Integer id);
}
