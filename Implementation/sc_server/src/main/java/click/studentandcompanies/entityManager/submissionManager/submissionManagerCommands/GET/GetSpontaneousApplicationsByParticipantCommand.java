package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.List;

public class GetSpontaneousApplicationsByParticipantCommand implements SubmissionManagerCommand<List<SpontaneousApplication>> {

    SpontaneousApplicationRepository spontaneousApplicationRepository;
    UserManager userManager;
    String userID;

    public GetSpontaneousApplicationsByParticipantCommand(SpontaneousApplicationRepository spontaneousApplicationRepository, UserManager userManager, String userID) {
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.userManager = userManager;
        this.userID = userID;
    }

    @Override
    public List<SpontaneousApplication> execute() throws NotFoundException, NoContentException, BadInputException {
        UserType type = userManager.getUserType(userID);
        List<SpontaneousApplication> applications = switch (type) {
            case STUDENT -> spontaneousApplicationRepository.getSpontaneousApplicationByStudent_Id(userID);
            case COMPANY -> spontaneousApplicationRepository.findSpontaneousApplicationByCompanyId(userID);
            case UNIVERSITY -> throw new BadInputException("User is not a company or student");
            default -> throw new NotFoundException("User not found");
        };
        if (applications.isEmpty()) throw new NoContentException("No spontaneous applications found");
        return applications;
    }
}
