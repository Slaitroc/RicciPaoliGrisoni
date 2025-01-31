package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.CommunicationTypeEnum;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.WrongStateException;

import java.util.Map;

public class CreateCommunicationCommand implements CommunicationManagerCommands<Communication> {
    private final CommunicationRepository communicationRepository;
    private final UserManager userManager;
    private final Map<String, Object> payload;

    public CreateCommunicationCommand(CommunicationRepository communicationRepository, UserManager userManager, Map<String, Object> payload) {
        this.communicationRepository = communicationRepository;
        this.userManager = userManager;
        this.payload = payload;
    }

    @Override
    public Communication execute() {
        validateInput(payload);
        return communicationRepository.save(createCommunication(payload));
    }

    //payload will contain the 'student_id', 'internshipOffer_id', 'university_id', 'title', 'communication_type'
    private void validateInput(Map<String, Object> payload) {
        if (payload.get("user_id") == null) {
            System.out.println("User id not found");
            throw new BadInputException("User id not found");
        }
        try {
            ParticipantTypeEnum.valueOf((String) payload.get("user_id"));
        } catch (IllegalArgumentException e) {
            System.out.println("Only Student or Company can create communication");
            throw new BadInputException("Only Student or Company can create communication");
        }
        ParticipantTypeEnum participantType = ParticipantTypeEnum.valueOf((String) payload.get("user_id"));
        validatePayloadByParticipantType(payload, participantType);
        if (payload.get("internshipOfferID") == null) {
            System.out.println("Internship offer id not found");
            throw new BadInputException("Internship offer id not found");
        }
        InternshipPosOffer internshipPosOffer = userManager.getInternshipPosOfferById((Integer) payload.get("internshipOfferID"));
        if (internshipPosOffer == null) {
            System.out.println("Internship offer not found");
            throw new NotFoundException("Internship offer not found");
        }

        if (payload.get("title") == null) {
            System.out.println("Title not found");
            throw new BadInputException("Title not found");
        }
        if (payload.get("content") == null) {
            System.out.println("Content not found");
            throw new BadInputException("Content not found");
        }
        if (payload.get("communicationType") == null) {
            System.out.println("Communication type not found");
            throw new BadInputException("Communication type not found");
        }
        try{
            CommunicationTypeEnum communicationType = CommunicationTypeEnum.valueOf((String) payload.get("communicationType"));
            if(communicationType == CommunicationTypeEnum.closed){
                System.out.println("Type cannot be closed");
                throw new WrongStateException("Type cannot be closed");
            }
        }catch (IllegalArgumentException e){
            System.out.println("Unknown communication type");
            throw new BadInputException("Unknown communication type");
        }
    }

    private void validatePayloadByParticipantType(Map<String, Object> payload, ParticipantTypeEnum participantType) {
        switch (participantType) {
            case student -> {
                Student student = userManager.getStudentById((String) payload.get("user_id"));
                if (student == null) {
                    System.out.println("Student not found");
                    throw new NotFoundException("Student not found");
                }
            }
            case company -> {
                Company company = userManager.getCompanyById((String) payload.get("user_id"));
                if (company == null) {
                    System.out.println("Company not found");
                    throw new NotFoundException("Company not found");
                }
            }
        }
    }

    private Communication createCommunication(Map<String, Object> payload) {
        Student student = null;
        Company company = null;
        ParticipantTypeEnum participantType = ParticipantTypeEnum.valueOf((String) payload.get("user_id"));
        if(participantType == ParticipantTypeEnum.student){
            student = userManager.getStudentById((String) payload.get("user_id"));
        }else{
            company = userManager.getCompanyById((String) payload.get("user_id"));
        }
        InternshipPosOffer internshipPosOffer = userManager.getInternshipPosOfferById((Integer) payload.get("internshipOfferID"));
        CommunicationTypeEnum communicationType = CommunicationTypeEnum.valueOf((String) payload.get("communicationType"));
        return new Communication(student, company, internshipPosOffer, (String) payload.get("title"), (String) payload.get("content"), communicationType);
    }
}
