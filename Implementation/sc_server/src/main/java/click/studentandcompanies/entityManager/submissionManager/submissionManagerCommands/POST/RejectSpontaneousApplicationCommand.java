package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST;

import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entity.dbEnum.SpontaneousApplicationStatusEnum;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;

import java.util.Map;

public class RejectSpontaneousApplicationCommand implements SubmissionManagerCommand<SpontaneousApplication> {

    private final SpontaneousApplicationRepository spontaneousApplicationRepository;
    private final Integer spontaneousApplicationID;
    private final Map<String, Object> payload;

    public RejectSpontaneousApplicationCommand(SpontaneousApplicationRepository spontaneousApplicationRepository, Integer spontaneousApplicationID, Map<String, Object> payload) {
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.spontaneousApplicationID = spontaneousApplicationID;
        this.payload = payload;
    }

    @Override
    public SpontaneousApplication execute() {
        SpontaneousApplication application = spontaneousApplicationRepository.getSpontaneousApplicationById(spontaneousApplicationID);
        if(application == null){
            throw new NotFoundException("Application not found");
        }
        if(!payload.get("user_id").equals(application.getStudent().getId())){
            throw new UnauthorizedException("You are not authorized to accept this application");
        }
        if(application.getStatus() == SpontaneousApplicationStatusEnum.toBeEvaluated){
            application.setStatus(SpontaneousApplicationStatusEnum.rejectedApplication);
        }else if (application.getStatus() == SpontaneousApplicationStatusEnum.acceptedApplication){
            throw new WrongStateException("Application already accepted");
        }else if (application.getStatus() == SpontaneousApplicationStatusEnum.rejectedApplication){
            throw new WrongStateException("Application already rejected");
        }
        return spontaneousApplicationRepository.save(application);
    }
}
