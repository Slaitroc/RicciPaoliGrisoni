package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.dbEnum.InternshipPosOfferStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import click.studentandcompanies.utils.exception.WrongStateException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AcceptInternshipPositionOfferCommand implements InterviewManagerCommand<InternshipPosOffer> {

    private final Integer intPosOffID;
    private final InternshipPosOfferRepository internshipPosOfferRepository;
    private final UserManager userManager;
    private final Map<String, Object> payload;

    public AcceptInternshipPositionOfferCommand(Integer intPosOffID, Map<String, Object> payload, InternshipPosOfferRepository internshipPosOfferRepository, UserManager userManager) {
        this.intPosOffID = intPosOffID;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
        this.userManager = userManager;
        this.payload = payload;
    }

    @Override
    public InternshipPosOffer execute() throws NotFoundException, BadInputException, UnauthorizedException, WrongStateException {
        InternshipPosOffer internshipPosOffer = validateInput(payload, intPosOffID);
        internshipPosOffer.setStatus(InternshipPosOfferStatusEnum.accepted);
        return internshipPosOfferRepository.save(internshipPosOffer);
    }

    private InternshipPosOffer validateInput(Map<String, Object> payload, Integer intPosOffID){
        if(payload.get("student_id") == null){
            throw new BadInputException("student_id not provided correctly");
        }
        UserType type = userManager.getUserType((String) payload.get("student_id"));
        if(type == UserType.UNKNOWN){
            throw new BadInputException("Unknown user");
        }else if(type != UserType.STUDENT){
            throw new UnauthorizedException("Only students can accept internship position offers");
        }
        List<InternshipPosOffer> internshipPosOffers = internshipPosOfferRepository.findAll();
        if(internshipPosOffers.stream().map(InternshipPosOffer::getStatus).toList().contains(InternshipPosOfferStatusEnum.accepted)){
            throw new WrongStateException("Another internship position offer has already been accepted");
        }
        InternshipPosOffer internshipPosOffer = internshipPosOffers.stream().filter(intPosOff -> Objects.equals(intPosOff.getId(), intPosOffID)).findFirst().orElse(null);
        return checkSelectedIntPosOffStatus(internshipPosOffer);
    }

    private InternshipPosOffer checkSelectedIntPosOffStatus(InternshipPosOffer internshipPosOffer) throws WrongStateException, UnauthorizedException {
        if(internshipPosOffer == null){
            throw new NotFoundException("Internship Position Offer not found");
        }
        if(internshipPosOffer.getStatus() != InternshipPosOfferStatusEnum.pending){
            throw new WrongStateException("Internship Position Offer is not pending");
        }
        if(!(userManager.getStudentIDByInternshipPosOfferID(intPosOffID).equals(payload.get("student_id")))){
            throw new UnauthorizedException("Student is trying to accept an internship position offer that is not theirs");
        }
        return internshipPosOffer;
    }
}
