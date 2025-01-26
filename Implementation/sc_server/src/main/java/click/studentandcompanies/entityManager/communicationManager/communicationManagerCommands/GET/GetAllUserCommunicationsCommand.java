package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.List;

public class GetAllUserCommunicationsCommand implements CommunicationManagerCommands<List<Communication>> {

    UserManager userManager;
    CommunicationRepository communicationRepository;
    String userID;

    public GetAllUserCommunicationsCommand(UserManager userManager, CommunicationRepository communicationRepository, String userID) {
        this.userManager = userManager;
        this.communicationRepository = communicationRepository;
        this.userID = userID;
    }

    public List<Communication> execute() throws NotFoundException, NoContentException {
        UserType type = userManager.getUserType(userID);
        List<Communication> communications =
                switch (type) {
                    case STUDENT -> communicationRepository.findCommunicationByStudent_Id(userID);
                    case COMPANY -> communicationRepository.findCommunicationByCompany_Id(userID);
                    case UNIVERSITY -> communicationRepository.findCommunicationByUniversity_Id(userID);
                    default -> throw new NotFoundException("User not found");
                };
        if (communications.isEmpty()) {
            throw new NoContentException("No communications found");
        }else {
            return communications;
        }
    }
}
