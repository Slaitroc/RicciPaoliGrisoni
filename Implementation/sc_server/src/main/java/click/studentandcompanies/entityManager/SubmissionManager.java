package click.studentandcompanies.entityManager;
import click.studentandcompanies.entity.Cv;
import click.studentandcompanies.entity.InternshipOffer;
import click.studentandcompanies.entity.SpontaneousApplication;
import click.studentandcompanies.entityManager.entityRepository.CvRepository;
import click.studentandcompanies.entityManager.entityRepository.InternshipOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.SpontaneousApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionManager {
    private final CvRepository cvRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final SpontaneousApplicationRepository spontaneousApplicationRepository;
    private final UserManager userManager;

    public SubmissionManager(CvRepository cvRepository, InternshipOfferRepository internshipOfferRepository, SpontaneousApplicationRepository spontaneousApplicationRepository, UserManager userManager) {
        this.cvRepository = cvRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.spontaneousApplicationRepository = spontaneousApplicationRepository;
        this.userManager = userManager;
    }

    public List<InternshipOffer> getInternshipsByCompany(Integer companyID) {
        System.out.println("Getting the Internships of company: " + companyID);
        List<InternshipOffer> list = internshipOfferRepository.getInternshipOfferByCompany_Id(companyID);
        System.out.println("Internships queried: " + list);
        return list;
    }

    public Cv getCvByStudent(Integer studentID) {
        return cvRepository.getCvByStudent_Id(studentID);
    }
}
