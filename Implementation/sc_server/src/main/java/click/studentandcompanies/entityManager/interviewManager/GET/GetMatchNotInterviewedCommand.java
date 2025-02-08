package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.Company;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.exception.BadInputException;

import java.util.ArrayList;
import java.util.List;

public class GetMatchNotInterviewedCommand implements InterviewManagerCommand<List<Interview>> {
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;
    private final String companyID;

    public GetMatchNotInterviewedCommand(InterviewRepository interviewRepository, UserManager userManager, String companyID) {
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
        this.companyID = companyID;
    }

    @Override
    public List<Interview> execute() {
        Company company = userManager.getCompanyById(companyID);
        if(company == null){
            throw new BadInputException("Company not found");
        }
        List<Interview> allInterviews = interviewRepository.findAll();
        List<Interview> okInterviews = new ArrayList<>();
        for(Interview interview : allInterviews){
            if(interview.getRecommendation() != null){
                if(interview.getRecommendation().getInternshipOffer().getCompany().getId().equals(companyID) && interview.getStatus().equals(InterviewStatusEnum.toBeSubmitted)){
                    okInterviews.add(interview);
                }
            }else{ //if the interview is a spontaneous application
                if(interview.getSpontaneousApplication().getInternshipOffer().getCompany().getId().equals(companyID) && interview.getStatus().equals(InterviewStatusEnum.toBeSubmitted)){
                    okInterviews.add(interview);
                }
            }
        }
        return okInterviews;
    }

}
