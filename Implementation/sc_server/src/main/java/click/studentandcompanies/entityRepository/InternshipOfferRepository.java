package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.InternshipOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipOfferRepository extends JpaRepository<InternshipOffer, Integer> {
    List<InternshipOffer> getInternshipOfferByCompanyId(String companyId);

    InternshipOffer getInternshipOfferById(Integer id);

    void removeInternshipOfferById(Integer id);
}