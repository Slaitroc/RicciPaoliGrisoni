package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.GET;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.ArrayList;
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
        List<Communication> allCommunications = communicationRepository.findAll();
        //okCommunications is the list of communications that the user is involved in
        List<Communication> okCommunications = new ArrayList<>();
        switch (type) {
            case STUDENT -> {
                for (Communication communication : allCommunications) {
                    Interview interviewOfComm = communication.getInternshipPosOff().getInterview();
                    if (interviewOfComm.getRecommendation() != null && interviewOfComm.getRecommendation().getCv().getStudent().getId().equals(userID)) {
                        okCommunications.add(communication);
                    }else if(interviewOfComm.getSpontaneousApplication() != null && interviewOfComm.getSpontaneousApplication().getStudent().getId().equals(userID)){
                        okCommunications.add(communication);
                    }
                }
            }
            case COMPANY -> {
                for (Communication communication : allCommunications) {
                    Interview interviewOfComm = communication.getInternshipPosOff().getInterview();
                    if (interviewOfComm.getRecommendation() != null && interviewOfComm.getRecommendation().getInternshipOffer().getCompany().getId().equals(userID)) {
                        okCommunications.add(communication);
                    }else if(interviewOfComm.getSpontaneousApplication() != null && interviewOfComm.getSpontaneousApplication().getInternshipOffer().getCompany().getId().equals(userID)){
                        okCommunications.add(communication);
                    }
                }
            }
            case UNIVERSITY -> {
                for (Communication communication : allCommunications) {
                    Interview interviewOfComm = communication.getInternshipPosOff().getInterview();
                    if (interviewOfComm.getRecommendation() != null && interviewOfComm.getRecommendation().getCv().getStudent().getUniversity().getId().equals(userID)) {
                        okCommunications.add(communication);
                    }else if(interviewOfComm.getSpontaneousApplication() != null && interviewOfComm.getSpontaneousApplication().getStudent().getUniversity().getId().equals(userID)){
                        okCommunications.add(communication);
                    }
                }
            }
            default -> throw new NotFoundException("User not found");
        }
        if (okCommunications.isEmpty()) {
            throw new NoContentException("No communications found");
        } else {
            return okCommunications;
        }
    }
}
