package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {
    List<Communication> findCommunicationByUniversity_Id(String userID);

    @Query("SELECT c FROM Communication c WHERE c.internshipOffer.company.id = :userID")
    List<Communication> findCommunicationByCompany_Id(@Param("userID") String userID);

    List<Communication> findCommunicationByStudent_Id(String userID);

    Communication getCommunicationById(Integer commID);
}