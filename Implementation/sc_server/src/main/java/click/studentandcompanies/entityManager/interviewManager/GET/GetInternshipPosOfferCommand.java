package click.studentandcompanies.entityManager.interviewManager.GET;

import click.studentandcompanies.dto.DTO;
import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;

import java.util.List;
import java.util.Objects;

public class GetInternshipPosOfferCommand implements InterviewManagerCommand<List<InternshipPosOffer>> {
    private final String studentID;
    private final UserManager userManager;
    private final InternshipPosOfferRepository internshipPosOfferRepository;

    public GetInternshipPosOfferCommand(String studentID, InternshipPosOfferRepository internshipPosOfferRepository, UserManager userManager) {
        this.studentID = studentID;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
        this.userManager = userManager;
    }

    @Override
    public List<InternshipPosOffer> execute() {
        List<Interview> interviewsOfStudent = userManager.getInterviewsByStudentID(studentID);
        return interviewsOfStudent.stream().map(Interview::getInternshipPosOffer).filter(Objects::nonNull).toList();
    }
}
