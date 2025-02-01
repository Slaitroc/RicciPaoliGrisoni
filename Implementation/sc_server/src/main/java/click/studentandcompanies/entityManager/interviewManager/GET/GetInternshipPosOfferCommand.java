package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;

import java.util.List;
import java.util.Objects;

public class GetInternshipPosOfferCommand implements InterviewManagerCommand<List<InternshipPosOffer>> {
    private final String userID;
    private final UserManager userManager;
    private final InternshipPosOfferRepository internshipPosOfferRepository;

    public GetInternshipPosOfferCommand(String userID, InternshipPosOfferRepository internshipPosOfferRepository, UserManager userManager) {
        this.userID = userID;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
        this.userManager = userManager;
    }

    @Override
    public List<InternshipPosOffer> execute() {
        UserType userType = userManager.getUserType(userID);
        List<InternshipPosOffer> interviews;
        if(userType == UserType.COMPANY){
            return userManager.getInterviewsByCompanyID(userID).stream().map(Interview::getInternshipPosOffer).filter(Objects::nonNull).toList();
        }else if(userType == UserType.STUDENT){
            return userManager.getInterviewsByStudentID(userID).stream().map(Interview::getInternshipPosOffer).filter(Objects::nonNull).toList();
        }else{
            throw new BadInputException("User is not a student or a company");
        }
    }
}
