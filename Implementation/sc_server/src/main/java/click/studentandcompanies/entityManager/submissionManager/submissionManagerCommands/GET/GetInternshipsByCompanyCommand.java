package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.InternshipOfferRepository;

import java.util.List;

public class GetInternshipsByCompanyCommand {

    InternshipOfferRepository internshipOfferRepository;
    UserManager userManager;
    Integer companyID;

    public GetInternshipsByCompanyCommand(InternshipOfferRepository internshipOfferRepository, UserManager userManager, Integer companyID) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.userManager = userManager;
        this.companyID = companyID;
    }

    public List<InternshipOffer> execute() {
        if (userManager.getCompanyById(companyID) == null) {
            System.out.println("Company not found");
            throw new IllegalArgumentException("Company not found");
        }
        return internshipOfferRepository.getInternshipOfferByCompanyId(companyID);
    }
}
