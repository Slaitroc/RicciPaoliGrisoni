package click.studentandcompanies.entityManager.communicationManager;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET.GetAllUserCommunicationsCommand;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET.GetCommunicationCommand;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST.CreateCommunicationCommand;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST.TerminateCommunicationCommand;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommunicationManager {
    private final UserManager userManager;
    private final CommunicationRepository communicationRepository;

    public CommunicationManager(UserManager userManager, CommunicationRepository communicationRepository) {
        this.userManager = userManager;
        this.communicationRepository = communicationRepository;
    }

    public List<Communication> getAllUserCommunications(Integer userID) throws NotFoundException, NoContentException {
        return new GetAllUserCommunicationsCommand(userManager, communicationRepository, userID).execute();
    }

    public Communication getCommunication(Integer commID, Integer userID) throws NotFoundException, UnauthorizedException {
        return new GetCommunicationCommand(communicationRepository, commID, userID).execute();
    }

    public Communication createCommunication(Map<String, Object> payload) throws NotFoundException, BadInputException{
        return new CreateCommunicationCommand(communicationRepository, userManager, payload).execute();
    }

    public Communication terminateCommunication(int communicationID, Map<String, Object> payload) throws BadInputException, UnauthorizedException{
        return new TerminateCommunicationCommand(communicationID, communicationRepository, payload, userManager).execute();
    }
}
