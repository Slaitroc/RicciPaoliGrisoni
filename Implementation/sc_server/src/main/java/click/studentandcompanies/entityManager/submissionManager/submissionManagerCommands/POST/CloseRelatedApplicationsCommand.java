package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST;

import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.entityRepository.SpontaneousApplicationRepository;

public class CloseRelatedApplicationsCommand implements SubmissionManagerCommand<Void> {

    private final SpontaneousApplicationRepository spontaneousApplicationRepository;
    private final Integer internshipID;

    public CloseRelatedApplicationsCommand(SpontaneousApplicationRepository spontaneousApplicationRepository, Integer internshipID) {
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.internshipID = internshipID;
    }

    @Override
    public Void execute() {
        spontaneousApplicationRepository.removeSpontaneousApplicationByInternshipOffer_Id(internshipID);
        return null;
    }
}
