package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET;

import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.utils.UserType;

import java.util.List;

public class getSpontaneousApplicationsByParticipantCommand implements SubmissionManagerCommand {

    SpontaneousApplicationRepository spontaneousApplicationRepository;
    UserManager userManager;
    Integer userID;

    public getSpontaneousApplicationsByParticipantCommand(SpontaneousApplicationRepository spontaneousApplicationRepository, UserManager userManager, Integer userID) {
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.userManager = userManager;
        this.userID = userID;
    }

    @Override
    public List<SpontaneousApplication> execute() {
        UserType type = userManager.getUserType(userID);
        return switch (type) {
            case STUDENT -> spontaneousApplicationRepository.getSpontaneousApplicationByStudent_Id(userID);
            case COMPANY -> spontaneousApplicationRepository.findSpontaneousApplicationByCompanyId(userID);
            case UNIVERSITY -> throw new IllegalCallerException("User is not a company or student");
            default -> throw new IllegalArgumentException("User not found");
        };
    }
}
