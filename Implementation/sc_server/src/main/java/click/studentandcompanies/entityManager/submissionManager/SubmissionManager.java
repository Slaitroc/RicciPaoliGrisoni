package click.studentandcompanies.entityManager.submissionManager;
import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.GET.getSpontaneousApplicationByCompanyCommand;
import click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST.updateCVCommand;
import click.studentandcompanies.entityManager.submissionManager.submissionManagerCommands.POST.updateInternshipOfferCommand;
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
        System.out.println("Getting the Internships of company: " + companyID);
        List<InternshipOffer> list = internshipOfferRepository.getInternshipOfferByCompanyId(companyID);
        System.out.println("Internships queried: " + list);
        return list;
    }

    public Cv getCvByStudent(Integer studentID) {
        return cvRepository.getCvByStudent_Id(studentID);
    }

    public Cv updateCvCall(Map<String, Object> payload) {
        return new updateCVCommand(userManager, cvRepository, payload).execute();
    }

    public InternshipOffer updateInternshipOffer(Map<String, Object> payload) throws IllegalCallerException, IllegalArgumentException, SecurityException {
        return new updateInternshipOfferCommand(userManager, internshipOfferRepository, payload).execute();
    }

    public List<SpontaneousApplication> getSpontaneousApplicationByStudent(Integer studentID){
        return spontaneousApplicationRepository.getSpontaneousApplicationByStudent_Id(studentID);
    }

    public List<SpontaneousApplication> getSpontaneousApplicationByCompany(Integer companyID) {
        return new getSpontaneousApplicationByCompanyCommand(
                spontaneousApplicationRepository, internshipOfferRepository, companyID).execute();
    }
}
