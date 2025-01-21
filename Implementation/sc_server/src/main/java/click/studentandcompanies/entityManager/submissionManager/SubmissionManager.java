package click.studentandcompanies.entityManager.submissionManager;
import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityRepository.CvRepository;
import click.studentandcompanies.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET.*;
import click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST.*;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
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
        return new GetInternshipsByCompanyCommand(internshipOfferRepository, userManager, companyID).execute();
    }

    public Cv getCvByStudent(Integer studentID) {
        return new GetCvByStudentCommand(cvRepository, userManager, studentID).execute();
    }

    public Cv updateCvCall(Map<String, Object> payload) throws BadInputException, NotFoundException {
        return new UpdateCVCommand(userManager, cvRepository, payload).execute();
    }

    public InternshipOffer updateInternshipOffer(Map<String, Object> payload) throws UnauthorizedException, BadInputException, NotFoundException {
        return new UpdateInternshipOfferCommand(userManager, internshipOfferRepository, payload).execute();
    }

    public List<SpontaneousApplication> getSpontaneousApplicationsByParticipant(Integer studentID) throws BadInputException, NotFoundException {
        return new GetSpontaneousApplicationsByParticipantCommand(spontaneousApplicationRepository, userManager, studentID).execute();
    }

    public SpontaneousApplication submitSpontaneousApplication(Map<String, Object> payload, int internshipOfferID) throws BadInputException, NotFoundException {
        return new SubmitSpontaneousApplicationCommand(payload, userManager, spontaneousApplicationRepository, internshipOfferRepository, internshipOfferID).execute();
    }
}
