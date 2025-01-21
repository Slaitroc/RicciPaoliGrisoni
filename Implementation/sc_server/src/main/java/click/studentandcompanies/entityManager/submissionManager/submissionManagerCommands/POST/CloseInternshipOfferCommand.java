package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST;

import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.Map;

public class CloseInternshipOfferCommand implements SubmissionManagerCommand<InternshipOffer>{

    InternshipOfferRepository internshipOfferRepository;
    Integer internshipID;
    Map<String, Object> payload;

    public CloseInternshipOfferCommand(InternshipOfferRepository internshipOfferRepository, Integer internshipID, Map<String, Object> payload) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipID = internshipID;
        this.payload = payload;
    }

    @Override
    public InternshipOffer execute() {
        InternshipOffer offer = internshipOfferRepository.getInternshipOfferById(internshipID);
        if(offer == null){
            throw new NotFoundException("Internship not found");
        }if(payload.get("company_id") != offer.getCompany().getId()){
            throw new UnauthorizedException("You are not authorized to close this internship offer");
        }
        internshipOfferRepository.removeInternshipOfferById(internshipID);
        return offer;
    }
}
