package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;


public class GetCommunicationCommand implements CommunicationManagerCommands<Communication> {

    CommunicationRepository communicationRepository;
    Integer commID;
    String userID;

    public GetCommunicationCommand(CommunicationRepository communicationRepository, Integer commID, String userID) {
        this.communicationRepository = communicationRepository;
        this.commID = commID;
        this.userID = userID;
    }

    @Override
    public Communication execute() throws NotFoundException, UnauthorizedException{
        Communication communication = communicationRepository.getCommunicationById(commID);
        if (communication == null) {
            throw new NotFoundException("Communication not found");
        } else
            if(communication.getStudent().getId().equals(userID) ||
                communication.getInternshipOffer().getCompany().getId().equals(userID) ||
                communication.getUniversity().getId().equals(userID)) {
            return communication;
        } else {
            throw new UnauthorizedException("User not authorized to access this communication");
        }
    }
}
