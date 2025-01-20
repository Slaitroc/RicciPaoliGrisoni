package click.studentandcompanies.entityManager.submissionManager;
import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET.*;
import click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubmissionManager {
    private final CvRepository cvRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final SpontaneousApplicationRepository spontaneousApplicationRepository;
    private final UserManager userManager;

    @PersistenceContext
    private EntityManager entityManager;

    public SubmissionManager(CvRepository cvRepository, InternshipOfferRepository internshipOfferRepository, SpontaneousApplicationRepository spontaneousApplicationRepository, UserManager userManager) {
        this.cvRepository = cvRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.userManager = userManager;
    }

    public List<InternshipOffer> getInternshipsByCompany(Integer companyID) {
        return new getInternshipsByCompanyCommand(internshipOfferRepository, userManager, companyID).execute();
    }

    public Cv getCvByStudent(Integer studentID) {
        return new getCvByStudentCommand(cvRepository, userManager, studentID).execute();
    }

    public Cv updateCvCall(Map<String, Object> payload) {
        return new updateCVCommand(userManager, cvRepository, payload).execute();
    }

    public InternshipOffer updateInternshipOffer(Map<String, Object> payload) throws IllegalCallerException, IllegalArgumentException, SecurityException {
        return new updateInternshipOfferCommand(userManager, internshipOfferRepository, payload).execute();
    }

    public List<SpontaneousApplication> getSpontaneousApplicationsByParticipant(Integer studentID) throws IllegalCallerException, IllegalArgumentException {
        return new getSpontaneousApplicationsByParticipantCommand(spontaneousApplicationRepository, userManager, studentID).execute();
    }
}
