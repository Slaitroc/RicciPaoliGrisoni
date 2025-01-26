package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.List;

public class GetInternshipsByCompanyCommand {

    InternshipOfferRepository internshipOfferRepository;
    UserManager userManager;
    String companyID;

    public GetInternshipsByCompanyCommand(InternshipOfferRepository internshipOfferRepository, UserManager userManager, String companyID) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.userManager = userManager;
        this.companyID = companyID;
    }

    public List<InternshipOffer> execute() throws NotFoundException, NoContentException {
        if (userManager.getCompanyById(companyID) == null) {
            System.out.println("Company not found");
            throw new NotFoundException("Company not found");
        }
        List<InternshipOffer> internshipOffers = internshipOfferRepository.getInternshipOfferByCompanyId(companyID);
        if (internshipOffers.isEmpty()) throw new NoContentException("No Internship offers found");
        return internshipOffers;
    }
}
