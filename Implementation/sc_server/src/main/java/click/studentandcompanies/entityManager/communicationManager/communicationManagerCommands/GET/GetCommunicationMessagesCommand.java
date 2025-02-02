package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.entityRepository.MessageRepository;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.List;


public class GetCommunicationMessagesCommand implements CommunicationManagerCommands<List<Message>> {

    MessageRepository messageRepository;
    CommunicationRepository communicationRepository;
    Integer commID;
    String userID;

    public GetCommunicationMessagesCommand(MessageRepository messageRepository, CommunicationRepository communicationRepository, Integer commID, String userID) {
        this.messageRepository = messageRepository;
        this.communicationRepository = communicationRepository;
        this.commID = commID;
        this.userID = userID;
    }

    @Override
    public List<Message> execute() throws NotFoundException, UnauthorizedException{
        Communication communication = communicationRepository.findById(commID).orElseThrow(() -> new NotFoundException("Communication not found"));

        List<Message> messages = messageRepository.getMessagesByCommunication_IdOrderByTimeStamp(commID);
        if (messages.isEmpty()) {
            throw new NoContentException("No messages in communication");
        }else{
            Interview interview = communication.getInternshipPosOff().getInterview();
            Recommendation recommendation = interview.getRecommendation();
            SpontaneousApplication spontaneousApplication = interview.getSpontaneousApplication();

            if(recommendation != null){
                Student student = recommendation.getCv().getStudent();
                if(student.getId().equals(userID) || student.getUniversity().getId().equals(userID)){
                    return messages;
                } else if (recommendation.getInternshipOffer().getCompany().getId().equals(userID)){
                    return messages;
                }
            }else if(spontaneousApplication != null) {
                Student student = spontaneousApplication.getStudent();
                if (student.getId().equals(userID) || student.getUniversity().getId().equals(userID)) {
                    return messages;
                } else if (spontaneousApplication.getInternshipOffer().getCompany().getId().equals(userID)) {
                    return messages;
                }
            }
            throw new UnauthorizedException("User not authorized to access this communication");
        }
    }
}
