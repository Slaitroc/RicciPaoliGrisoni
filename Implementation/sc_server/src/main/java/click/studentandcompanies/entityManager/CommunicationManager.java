package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.Communication;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Communication getCommunication(Integer commID, Integer userID) throws NotFoundException, UnauthorizedException {
        Optional<Communication> communication = communicationRepository.findById(commID);
        if (communication.isEmpty()) {
            throw new NotFoundException("Communication not found");
        } else if(communication.get().getStudent().getId().equals(userID) || communication.get().getInternshipOffer().getCompany().getId().equals(userID) || communication.get().getUniversity().getId().equals(userID)){
            return communication.get();
        } else {
            throw new UnauthorizedException("User not authorized to access this communication");
        }
    }
}
