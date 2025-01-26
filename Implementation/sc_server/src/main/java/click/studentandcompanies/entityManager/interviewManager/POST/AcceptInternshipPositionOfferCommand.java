package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;

import java.util.Map;
import java.util.Objects;

public class AcceptInternshipPositionOfferCommand implements InterviewManagerCommand<InternshipPosOffer> {

    private final Integer intPosOffID;
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;
    private final Integer userID;

    public AcceptInternshipPositionOfferCommand(Integer intPosOffID, Map<String, Object> payload, InterviewRepository interviewRepository, UserManager userManager) {
        this.intPosOffID = intPosOffID;
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
        // Check if the payload is in the correct format
        try {
            this.userID = (Integer) payload.get("userID");
        } catch (Exception e) {
            throw new BadInputException("userID not provided correctly");
        }
    }

    @Override
    public InternshipPosOffer execute() throws NotFoundException, BadInputException, UnauthorizedException, WrongStateException {
        UserType type = userManager.getUserType(userID);
        // Check if the user is a student
        if (type == UserType.STUDENT) {
            Interview interview = interviewRepository.getInterviewByInternshipPosOffer_Id(intPosOffID);
            if (interview == null) {
                throw new NotFoundException("Interview not found");
            }
            InternshipPosOffer internshipPosOffer = getInternshipPosOffer(interview);
            internshipPosOffer.setAcceptance(true);
            return internshipPosOffer;
        } if (type == UserType.UNKNOWN) {
            throw new BadInputException("User not found");
        } else {
            throw new UnauthorizedException("User not authorized to accept internship position offer");
        }
    }

    private InternshipPosOffer getInternshipPosOffer(Interview interview) throws NotFoundException, UnauthorizedException, WrongStateException {
        InternshipPosOffer internshipPosOffer = interview.getInternshipPosOffer();
        if(internshipPosOffer == null) {
            throw new NotFoundException("Internship Position Offer not found");
        }
        // Check if the student whose ID is passed through the payload is the same as the student who owns the Internship Position Offer
        if(interview.getRecommendation() != null && !Objects.equals(interview.getRecommendation().getCv().getStudent().getId(), userID)) {
            throw new UnauthorizedException("User not authorized to accept internship position offer");
        }
        if(interview.getSpontaneousApplication() != null && !Objects.equals(interview.getSpontaneousApplication().getStudent().getId(), userID)) {
            throw new UnauthorizedException("User not authorized to accept internship position offer");
        }
        // Check if the Internship Position Offer has already been accepted
        if(internshipPosOffer.getAcceptance()) {
            throw new WrongStateException("Internship Position Offer already accepted");
        }
        return internshipPosOffer;
    }
}
