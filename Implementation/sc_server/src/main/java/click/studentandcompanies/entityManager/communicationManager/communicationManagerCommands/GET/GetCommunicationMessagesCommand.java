package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.Message;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.MessageRepository;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.List;


public class GetCommunicationMessagesCommand implements CommunicationManagerCommands<List<Message>> {

    MessageRepository messageRepository;
    Integer commID;
    String userID;

    public GetCommunicationMessagesCommand(MessageRepository messageRepository, Integer commID, String userID) {
        this.messageRepository = messageRepository;
        this.commID = commID;
        this.userID = userID;
    }

    @Override
    public List<Message> execute() throws NotFoundException, UnauthorizedException{
        List<Message> messages = messageRepository.getMessagesByCommunication_Id(commID);
        Communication communication = messages.getFirst().getCommunication();
        if (communication == null) {
            throw new NotFoundException("Communication not found");
        } else
            if(communication.getStudent().getId().equals(userID) ||
                communication.getInternshipOffer().getCompany().getId().equals(userID) ||
                communication.getUniversity().getId().equals(userID)) {
            return messages;
        } else {
            throw new UnauthorizedException("User not authorized to access this communication");
        }
    }
}
