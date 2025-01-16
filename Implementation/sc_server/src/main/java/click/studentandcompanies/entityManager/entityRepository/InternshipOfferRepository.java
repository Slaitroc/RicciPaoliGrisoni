package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.InternshipOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipOfferRepository extends JpaRepository<InternshipOffer, Integer> {
    List<InternshipOffer> getInternshipOfferByCompany_Id(Integer companyId);
}