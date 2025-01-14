package click.studentandcompanies.entityManager.entityRepository;

import click.studentandcompanies.entity.InternshipOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipOfferRepository extends JpaRepository<InternshipOffer, Integer> {
}