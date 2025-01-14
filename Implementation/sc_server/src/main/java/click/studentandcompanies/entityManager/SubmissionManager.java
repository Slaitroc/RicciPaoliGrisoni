package click.studentandcompanies.entityManager;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.entityRepository.InternshipOfferRepository;

public class SubmissionManager {
    private final CvRepository cvRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final SpontaneousApplication spontaneousApplication;
    private final UserManager userManager;

    public SubmissionManager(CvRepository cvRepository, InternshipOfferRepository internshipOfferRepository, SpontaneousApplication spontaneousApplication, UserManager userManager) {
        this.cvRepository = cvRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.spontaneousApplication = spontaneousApplication;
        this.userManager = userManager;
    }
}
