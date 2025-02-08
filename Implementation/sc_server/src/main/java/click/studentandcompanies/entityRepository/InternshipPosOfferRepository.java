package click.studentandcompanies.entityRepository;

import click.studentandcompanies.entity.InternshipPosOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipPosOfferRepository extends JpaRepository<InternshipPosOffer, Integer> {

    //NOTE weird warnings? dangerous?
    InternshipPosOffer getById(Integer id);

    InternshipPosOffer getInternshipPosOfferById(Integer id);
}