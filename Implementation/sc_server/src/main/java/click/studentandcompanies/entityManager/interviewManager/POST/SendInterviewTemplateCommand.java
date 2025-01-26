package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewTemplate;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.interviewManager.InterviewManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.entityRepository.InterviewTemplateRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.UnauthorizedException;
import org.hibernate.sql.Template;

import java.util.Map;

public class SendInterviewTemplateCommand implements InterviewManagerCommand<Interview> {
    private final InterviewRepository interviewRepository;
    private final InterviewTemplateRepository templateInterviewRepository;
    private final int interviewID;
    private final int templateID;
    private final Map<String, Object> payload;

    public SendInterviewTemplateCommand(int interviewID, int templateID, Map<String, Object> payload, InterviewRepository interviewRepository, InterviewTemplateRepository interviewTemplateRepository) {
        this.interviewRepository = interviewRepository;
        this.templateInterviewRepository = interviewTemplateRepository;
        this.interviewID = interviewID;
        this.templateID = templateID;
        this.payload = payload;
    }

    @Override
    public Interview execute() {
        Interview interview = interviewRepository.getInterviewById(interviewID);
        interviewInputValidation(interview);

        InterviewTemplate template = templateInterviewRepository.getInterviewTemplateById(templateID);
        templateInputValidation(template);

        interview.setInterviewTemplate(template);
        interview.setStatus(InterviewStatusEnum.submitted);
        return interviewRepository.save(interview);
    }

    private void interviewInputValidation(Interview interview){
        if(interview == null){
            System.out.println("Interview not found");
            throw new NotFoundException("Interview not found");
        }
        if(interview.getRecommendation() != null){
            if(interview.getRecommendation().getInternshipOffer().getCompany().getId() != payload.get("company_id")){
                System.out.println("Company is not a participant of this interview");
                throw new UnauthorizedException("The calling company is not a participant of this interview");
            }
        }else {
            if(interview.getSpontaneousApplication().getInternshipOffer().getCompany().getId() != payload.get("company_id")){
                System.out.println("Company is not a participant of this interview");
                throw new UnauthorizedException("The calling company is not a participant of this interview");
            }
        }
        if(interview.getStatus() != InterviewStatusEnum.toBeSubmitted || interview.getInterviewTemplate() != null){
            System.out.println("A template has already been set for this interview");
            throw new BadInputException("A template has already been set for this interview");
        }
    }

    private void templateInputValidation(InterviewTemplate template){
        if(template == null){
            System.out.println("Template not found");
            throw new NotFoundException("Template not found");
        }
        if(template.getCompany().getId() != payload.get("company_id")){
            System.out.println("Company is not the owner of the template");
            throw new UnauthorizedException("Company is not the owner of the template");
        }
    }
}
