package click.studentandcompanies.entityManager.communicationManager;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.Message;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET.GetAllUserCommunicationsCommand;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET.GetCommunicationMessagesCommand;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST.CreateCommunicationCommand;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST.CreateMessageCommand;
import click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST.TerminateCommunicationCommand;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.entityRepository.MessageRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommunicationManager {
    private final UserManager userManager;
    private final CommunicationRepository communicationRepository;
    private final MessageRepository messageRepository;

    public CommunicationManager(UserManager userManager, CommunicationRepository communicationRepository, MessageRepository messageRepository) {
        this.userManager = userManager;
        this.communicationRepository = communicationRepository;
        this.messageRepository = messageRepository;
    }

    public List<Communication> getAllUserCommunications(String userID) throws NotFoundException, NoContentException {
        return new GetAllUserCommunicationsCommand(userManager, communicationRepository, userID).execute();
    }

    public List<Message> getCommunicationMessages(Integer commID, String userID) throws NotFoundException, UnauthorizedException {
        return new GetCommunicationMessagesCommand(messageRepository, communicationRepository, commID, userID).execute();
    }

    public Communication createCommunication(Map<String, Object> payload) throws NotFoundException, BadInputException{
        return new CreateCommunicationCommand(communicationRepository, userManager, payload).execute();
    }

    public Message createMessage(String userID, Integer commID, Map<String, Object> payload) throws NotFoundException, UnauthorizedException, BadInputException{
        return new CreateMessageCommand(messageRepository, communicationRepository, userManager, userID, commID, payload).execute();
    }

    public Communication terminateCommunication(int communicationID, Map<String, Object> payload) throws BadInputException, UnauthorizedException{
        return new TerminateCommunicationCommand(communicationID, communicationRepository, payload, userManager).execute();
    }
}
