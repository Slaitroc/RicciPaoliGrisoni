package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.UserType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunicationManager {
    private final UserManager userManager;
    private final CommunicationRepository communicationRepository;

    public CommunicationManager(UserManager userManager, CommunicationRepository communicationRepository) {
        this.userManager = userManager;
        this.communicationRepository = communicationRepository;
    }

    public List<Communication> getAllUserCommunications(Integer userID) throws IllegalArgumentException {
        UserType type = userManager.getUserType(userID);
        return switch (type) {
            case STUDENT -> communicationRepository.findCommunicationByStudent_Id(userID);
            case COMPANY -> communicationRepository.findCommunicationByCompany_Id(userID);
            case UNIVERSITY -> communicationRepository.findCommunicationByUniversity_Id(userID);
            default -> throw new IllegalArgumentException("User not found");
        };
    }

    public Communication getCommunication(Integer commID) {
        return communicationRepository.findById(commID).orElseThrow(() -> new IllegalArgumentException("Communication not found"));
    }
}
