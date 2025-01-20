package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;

import java.util.ArrayList;
import java.util.List;

public class getSpontaneousApplicationByCompanyCommand implements SubmissionManagerCommand<List<SpontaneousApplication>> {
    SpontaneousApplicationRepository spontaneousApplicationRepository;
    InternshipOfferRepository internshipOfferRepository;
    UserManager userManager;
    Integer companyID;

    public getSpontaneousApplicationByCompanyCommand(SpontaneousApplicationRepository spontaneousApplicationRepository, InternshipOfferRepository internshipOfferRepository, UserManager userManager, Integer companyID) {
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.userManager = userManager;
        this.companyID = companyID;
    }

    @Override
    public List<SpontaneousApplication> execute() throws IllegalArgumentException{
        if (userManager.getCompanyById(companyID) == null) {
            System.out.println("Company not found");
            throw new IllegalArgumentException("Company not found");
        }
        //get a list of only the IDs of the internships offered by that company
        List<Integer> internshipsIDs = getInternshipsByCompany(companyID);
        System.out.println("IDs found: " + internshipsIDs);
        List<SpontaneousApplication> result = new ArrayList<>();
        //spontaneous applications are not linked directly to companies but only through Internships Offers
        for(Integer id : internshipsIDs){
            result.addAll(spontaneousApplicationRepository.getSpontaneousApplicationByInternshipOffer_Id(id));
        }
        return result;
    }

    private List<Integer> getInternshipsByCompany(Integer companyID) {
        System.out.println("Getting the Internships of company: " + companyID);
        List<Integer> idList = internshipOfferRepository.getInternshipOfferByCompanyId(companyID).stream().map(InternshipOffer::getId).toList();
        System.out.println("Internships queried: " + idList);
        return idList;
    }
}
