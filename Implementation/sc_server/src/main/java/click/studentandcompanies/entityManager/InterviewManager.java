package click.studentandcompanies.entityManager;

import click.studentandcompanies.entityManager.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityManager.entityRepository.InterviewRepository;
import click.studentandcompanies.entityManager.entityRepository.InterviewTemplateRepository;

public class InterviewManager {
    private final InterviewRepository interviewRepository;
    private final UserManager userManager;
    private final InterviewTemplateRepository interviewTemplateRepository;
    private final InternshipPosOfferRepository internshipPosOfferRepository;

    public InterviewManager(InterviewRepository interviewRepository, UserManager userManager, InterviewTemplateRepository interviewTemplateRepository, InternshipPosOfferRepository internshipPosOfferRepository) {
        this.interviewRepository = interviewRepository;
        this.userManager = userManager;
        this.interviewTemplateRepository = interviewTemplateRepository;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
    }
}
