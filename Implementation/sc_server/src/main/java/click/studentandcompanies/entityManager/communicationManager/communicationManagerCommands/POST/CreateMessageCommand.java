package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST;

import click.studentandcompanies.entity.*;
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
        //System.out.println("type: " + type);
        //here I check if the communication exists
        Communication communication = communicationRepository.findById(commID).orElseThrow(() -> new NotFoundException("Communication not found"));
        Message message = new Message();

        Recommendation recommendationOfCommunication = communication.getInternshipPosOff().getInterview().getRecommendation();
        SpontaneousApplication spontaneousApplicationOfCommunication = communication.getInternshipPosOff().getInterview().getSpontaneousApplication();
        //complete the message with the communication and the body and save it
        if (payload.get("body") == null) {
            throw new BadInputException("body is required");
        }else{
            message.setBody((String) payload.get("body"));
        }
        setMessageName(type, recommendationOfCommunication, spontaneousApplicationOfCommunication, message);
        message.setCommunication(communication);
        return messageRepository.save(message);
    }

    private void setMessageName(UserType type, Recommendation recommendationOfCommunication, SpontaneousApplication spontaneousApplicationOfCommunication, Message message) throws UnauthorizedException {
        switch (type) {
            case STUDENT: {
                if(recommendationOfCommunication != null) {
                    if(!recommendationOfCommunication.getCv().getStudent().getId().equals(userID)){
                        throw new UnauthorizedException("User not in communication");
                    }else{
                        message.setSenderName(recommendationOfCommunication.getCv().getStudent().getName());
                    }
                }else{ //if the recommendation is null then the communication is a spontaneous application
                    if(!spontaneousApplicationOfCommunication.getStudent().getId().equals(userID)){
                        throw new UnauthorizedException("User not in communication");
                    }else{
                        message.setSenderName(spontaneousApplicationOfCommunication.getStudent().getName());
                    }
                }
                return;
            }
            case COMPANY: {
                if(recommendationOfCommunication!=null){
                    if(!recommendationOfCommunication.getInternshipOffer().getCompany().getId().equals(userID)) {
                        throw new UnauthorizedException("User not in communication");
                    }else {
                        message.setSenderName(recommendationOfCommunication.getInternshipOffer().getCompany().getName());
                    }
                }else{
                    if(!spontaneousApplicationOfCommunication.getInternshipOffer().getCompany().getId().equals(userID)) {
                        throw new UnauthorizedException("User not in communication");
                    }else{
                        message.setSenderName(spontaneousApplicationOfCommunication.getInternshipOffer().getCompany().getName());
                    }
                }
                return;
            }
            case UNIVERSITY: {
                if(recommendationOfCommunication!=null){
                    if(!recommendationOfCommunication.getCv().getStudent().getUniversity().getId().equals(userID)){
                        throw new UnauthorizedException("User not in communication");
                    }else{
                        message.setSenderName(recommendationOfCommunication.getCv().getStudent().getUniversity().getName());
                    }
                }else{
                    if(!spontaneousApplicationOfCommunication.getStudent().getUniversity().getId().equals(userID)) {
                        throw new UnauthorizedException("User not in communication");
                    }else{
                        message.setSenderName(spontaneousApplicationOfCommunication.getStudent().getUniversity().getName());
                    }
                }
                return;
            }
            default: throw new UnauthorizedException("User not found");
        }
    }
}
