package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.Message;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.entityRepository.MessageRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.Map;

public class CreateMessageCommand implements CommunicationManagerCommands<Message> {

    private final MessageRepository messageRepository;
    private final CommunicationRepository communicationRepository;
    private final UserManager userManager;
    private final String userID;
    private final Integer commID;
    private final Map<String, Object> payload;

    public CreateMessageCommand(MessageRepository messageRepository, CommunicationRepository communicationRepository, UserManager userManager, String userID, Integer commID, Map<String, Object> payload) {
        this.messageRepository = messageRepository;
        this.communicationRepository = communicationRepository;
        this.userManager = userManager;
        this.userID = userID;
        this.commID = commID;
        this.payload = payload;
    }

    @Override
    public Message execute() throws NotFoundException, UnauthorizedException {

        UserType type = userManager.getUserType(userID);
        //here I check if the communication exists
        Communication communication = communicationRepository.findById(commID).orElseThrow(() -> new NotFoundException("Communication not found"));
        Message message = new Message();

        //here check if the user is part of the communication and if it is set the sender name
        switch (type) {
            case STUDENT: {
                if(!communication.getStudent().getId().equals(userID)) throw new UnauthorizedException("User not in communication");
                else message.setSenderName(communication.getStudent().getName());
                break;
            }
            case COMPANY: {
                if(!communication.getInternshipOffer().getCompany().getId().equals(userID)) throw new UnauthorizedException("User not in communication");
                else message.setSenderName(communication.getInternshipOffer().getCompany().getName());
                break;
            }
            case UNIVERSITY: {
                if(!communication.getUniversity().getId().equals(userID)) throw new UnauthorizedException("User not in communication");
                else message.setSenderName(communication.getUniversity().getName());
                break;
            }
            default: throw new UnauthorizedException("User not found");
        }

        //complete the message with the communication and the body and save it
        message.setCommunication(communication);
        if (payload.get("body") == null) throw new BadInputException("body is required");
        message.setBody((String) payload.get("body"));
        messageRepository.save(message);

        return message;
    }
}
