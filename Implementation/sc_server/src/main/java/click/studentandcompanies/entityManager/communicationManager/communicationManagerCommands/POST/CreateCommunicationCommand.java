package click.studentandcompanies.entityManager.communicationManager.communicationManagerCommands.POST;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entity.dbEnum.CommunicationTypeEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.communicationManager.CommunicationManagerCommands;
import click.studentandcompanies.entityRepository.CommunicationRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.List;
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
        Student student = userManager.getStudentById((Integer) payload.get("student_id"));
        InternshipOffer internshipOffer = userManager.getInternshipOfferById((Integer) payload.get("internshipOffer_id"));
        University university = userManager.getUniversityById((Integer) payload.get("university_id"));
        CommunicationTypeEnum communicationType = CommunicationTypeEnum.valueOf((String) payload.get("communication_type"));
        Communication communication = new Communication(student, internshipOffer, university, (String) payload.get("title"), (String) payload.get("content"), communicationType);
        communicationRepository.save(communication);
        return communication;
    }

    //payload will contain the 'student_id', 'internshipOffer_id', 'university_id', 'title', 'content', 'communication_type'
    private void validateInput(Map<String, Object> payload) {
        if (payload.get("student_id") == null) {
            System.out.println("Student id not found");
            throw new BadInputException("Student id not found");
        }
        if (payload.get("internshipOffer_id") == null) {
            System.out.println("Internship offer id not found");
            throw new BadInputException("Internship offer id not found");
        }
        if (payload.get("university_id") == null) {
            System.out.println("University id not found");
            throw new BadInputException("University id not found");
        }
        if (payload.get("title") == null) {
            System.out.println("Title not found");
            throw new BadInputException("Title not found");
        }
        if (payload.get("content") == null) {
            System.out.println("Content not found");
            throw new BadInputException("Content not found");
        }
        if(payload.get("communication_type") == null){
            System.out.println("Communication type not found");
            throw new BadInputException("Communication type not found");
        }
        Student student = userManager.getStudentById((Integer) payload.get("student_id"));
        if (student == null) {
            System.out.println("Student not found");
            throw new NotFoundException("Student not found");
        }
        University university = userManager.getUniversityById((Integer) payload.get("university_id"));
        if (university == null) {
            System.out.println("University not found");
            throw new NotFoundException("University not found");
        }
        InternshipOffer internshipOffer = userManager.getInternshipOfferById((Integer) payload.get("internshipOffer_id"));
        if (internshipOffer == null) {
            System.out.println("Internship offer not found");
            throw new NotFoundException("Internship offer not found");
        }
        if(student.getUniversity() != university) {
            System.out.println("Student is not from the university");
            throw new BadInputException("Student is not from the university");
        }
        if(payload.get("communication_type") == null){
            System.out.println("Communication type not found");
            throw new BadInputException("Communication type not found");
        }else{
            try{
                CommunicationTypeEnum.valueOf((String) payload.get("communication_type"));
            }catch (IllegalArgumentException e){
                System.out.println("Communication type not found");
                throw new BadInputException("Communication type not found");
            }
        }
    }

}
