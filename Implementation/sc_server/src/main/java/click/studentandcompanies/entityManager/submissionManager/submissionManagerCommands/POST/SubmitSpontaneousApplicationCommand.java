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
import click.studentandcompanies.utils.exception.WrongStateException;

import java.util.List;
import java.util.Map;

public class SubmitSpontaneousApplicationCommand implements SubmissionManagerCommand<SpontaneousApplication> {
    String student_id;
    SpontaneousApplicationRepository spontaneousApplicationRepository;
    InternshipOfferRepository internshipOfferRepository;
    UserManager userManager;
    int internshipOfferID;

    public SubmitSpontaneousApplicationCommand(UserManager userManager, SpontaneousApplicationRepository spontaneousApplicationRepository, InternshipOfferRepository internshipOfferRepository, String student_id, int internshipOfferID) {
        this.userManager = userManager;
        this.student_id = student_id;
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipOfferID = internshipOfferID;
    }

    @Override
    public SpontaneousApplication execute() {
        InternshipOffer internshipOffer = internshipOfferRepository.getInternshipOfferById(internshipOfferID);
        if(internshipOffer == null){
            System.out.println("Internship offer not found");
            throw new NotFoundException("Internship offer not found");
        }
        Student student = userManager.getStudentById(student_id);
//        if(student == null){
//            System.out.println("Student not found");
//            throw new BadInputException("Bad studentID provided not found");
//        }

        if(!spontaneousApplicationRepository.getSpontaneousApplicationByStudent_IdAndInternshipOffer_Id(student_id, internshipOfferID).isEmpty()){
            System.out.println("A spontaneous application already exists for this offer");
            throw new WrongStateException("A spontaneous application already exists for this offer");
        }

        SpontaneousApplication spontaneousApplication = new SpontaneousApplication(student, internshipOffer, SpontaneousApplicationStatusEnum.toBeEvaluated);
        return spontaneousApplicationRepository.save(spontaneousApplication);
    }
}
