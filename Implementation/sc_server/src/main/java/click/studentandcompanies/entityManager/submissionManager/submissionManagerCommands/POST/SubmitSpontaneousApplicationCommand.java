package click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST;

import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entity.Student;
import click.studentandcompanies.entity.dbEnum.SpontaneousApplicationStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.SubmissionManagerCommand;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.List;
import java.util.Map;

public class SubmitSpontaneousApplicationCommand implements SubmissionManagerCommand<SpontaneousApplication> {
    Map<String, Object> payload;
    SpontaneousApplicationRepository spontaneousApplicationRepository;
    InternshipOfferRepository internshipOfferRepository;
    UserManager userManager;
    int internshipOfferID;

    public SubmitSpontaneousApplicationCommand(Map<String, Object> payload, UserManager userManager, SpontaneousApplicationRepository spontaneousApplicationRepository, InternshipOfferRepository internshipOfferRepository, int internshipOfferID) {
        this.payload = payload;
        this.userManager = userManager;
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipOfferID = internshipOfferID;}

    @Override
    public SpontaneousApplication execute() {
        InternshipOffer internshipOffer = internshipOfferRepository.getInternshipOfferById(internshipOfferID);
        if(internshipOffer == null){
            System.out.println("Internship offer not found");
            throw new NotFoundException("Internship offer not found");
        }
        if(payload.get("student_id")==null){
            System.out.println("Student id not found");
            throw new NotFoundException("Student id not found");
        }
        Student student = userManager.getStudentById((String) payload.get("student_id"));
        if(student == null){
            System.out.println("Student not found");
            throw new BadInputException("Bad studentID provided not found");
        }
        List<SpontaneousApplication> spontaneousApplications = spontaneousApplicationRepository.findAll();
        for(SpontaneousApplication sa : spontaneousApplications){
            if(sa.getStudent().getId().equals(student.getId()) && sa.getInternshipOffer().getId() == internshipOfferID){
                System.out.println("A spontaneous application for this student and this internship offer already exists");
                throw new BadInputException("A spontaneous application for this student and this internship offer already exists");
            }
        }
        SpontaneousApplication spontaneousApplication = new SpontaneousApplication(student, internshipOffer, SpontaneousApplicationStatusEnum.toBeEvaluated);
        return spontaneousApplicationRepository.save(spontaneousApplication);
    }
}
