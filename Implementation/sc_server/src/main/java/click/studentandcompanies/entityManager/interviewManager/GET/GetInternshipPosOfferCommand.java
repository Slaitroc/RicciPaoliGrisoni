package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.NoContentException;
import click.studentandcompanies.utils.exception.UnauthorizedException;

import java.util.List;
import java.util.Objects;

public class GetInternshipPosOfferCommand implements InterviewManagerCommand<List<InternshipPosOffer>> {
    private final String userID;
    private final UserManager userManager;

    public GetInternshipPosOfferCommand(String userID, UserManager userManager) {
        this.userID = userID;
        this.userManager = userManager;
    }

    @Override
    public List<InternshipPosOffer> execute() {
        UserType userType = userManager.getUserType(userID);
        List<InternshipPosOffer> internshipPosOffers;
        if (userType == UserType.COMPANY) {
            internshipPosOffers = userManager.getInterviewsByCompanyID(userID).stream()
                    .map(Interview::getInternshipPosOffer)
                    .filter(Objects::nonNull)
                    .toList();
        } else if (userType == UserType.STUDENT) {
            internshipPosOffers = userManager.getInterviewsByStudentID(userID).stream()
                    .map(Interview::getInternshipPosOffer)
                    .filter(Objects::nonNull)
                    .toList();
        } else {
            throw new UnauthorizedException("User is not a student or a company");
        }

        if (internshipPosOffers.isEmpty()) {
            throw new NoContentException("No internship position offers found for this user");
        }

        return internshipPosOffers;
    }
}
