package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.University;
import click.studentandcompanies.entity.dbEnum.CommunicationTypeEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.Map;

public class TerminateCommunicationCommand implements CommunicationManagerCommands<Communication> {
    private final CommunicationRepository communicationRepository;
    private final Map<String, Object> payload;
    private final int communicationID;
    private final UserManager userManager;
    public TerminateCommunicationCommand(int communicationID, CommunicationRepository communicationRepository, Map<String, Object> payload, UserManager userManager) {
        this.communicationRepository = communicationRepository;
        this.payload = payload;
        this.communicationID = communicationID;
        this.userManager = userManager;
    }

    @Override
    public Communication execute() {
        if(payload.get("university_id") == null){
            System.out.println("University ID is null");
            throw new BadInputException("University ID cannot be null");
        }
        University university = userManager.getUniversityById((int) payload.get("university_id"));
        Communication communication = communicationRepository.findById(communicationID).orElseThrow(() -> {
            System.out.println("Communication not found");
            return new BadInputException("Communication not found");
        });
        if(communication.getStudent().getUniversity() != university){
            System.out.println("Unauthorized university");
            throw new UnauthorizedException("Unauthorized university");
        }
        communication.setCommunicationType(CommunicationTypeEnum.closed);
        return communicationRepository.save(communication);
    }
}
