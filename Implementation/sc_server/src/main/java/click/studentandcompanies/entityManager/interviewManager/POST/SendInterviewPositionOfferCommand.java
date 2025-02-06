package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.dbEnum.InternshipPosOfferStatusEnum;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.WrongStateException;

import java.util.Map;

public class SendInterviewPositionOfferCommand implements InterviewManagerCommand<InternshipPosOffer> {
    private final int interviewID;
    private final Map<String, Object> payload;
    private final UserManager userManager;
    private final InterviewRepository interviewRepository;
    private final InternshipPosOfferRepository internshipPosOfferRepository;

    public SendInterviewPositionOfferCommand(int interviewID, Map<String, Object> payload, UserManager userManager, InterviewRepository interviewRepository, InternshipPosOfferRepository internshipPosOfferRepository) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.userManager = userManager;
        this.interviewRepository = interviewRepository;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
    }

    @Override
    public InternshipPosOffer execute() {
        Interview interview = interviewRepository.findById(interviewID).orElse(null);
        if(interview == null){
            System.out.println("Interview not found");
            throw new NotFoundException("Interview not found");
        }
        if(interview.getInternshipPosOffer() != null){
            System.out.println("Interview position offer already exists");
            throw new WrongStateException("Interview position offer already exists");
        }
        if(payload.get("company_id") == null){
            System.out.println("Company id is null");
            throw new BadInputException("Company id is null");
        }
        Company company = userManager.getCompanyById((String) payload.get("company_id"));
        if(company == null){
            System.out.println("Company not found");
            throw new NotFoundException("Company not found");
        }
        if(company != interview.getRecommendation().getInternshipOffer().getCompany()){
            System.out.println("The provided company is not the same as the company that made the offer");
            throw new BadInputException("The provided company is not the same as the company that made the offer");
        }
        if(interview.getStatus() != InterviewStatusEnum.passed){
            System.out.println("Interview status is not passed");
            throw new WrongStateException("Interview status is not passed");
        }
        InternshipPosOffer internshipPosOffer = new InternshipPosOffer();
        internshipPosOffer.setStatus(InternshipPosOfferStatusEnum.pending);
        internshipPosOffer.setInterview(interview);
        internshipPosOfferRepository.save(internshipPosOffer);
        interview.setInternshipPosOffer(internshipPosOffer);
        interviewRepository.save(interview);
        return internshipPosOffer;
    }
}
