package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;

import java.util.Map;

public class EvaluateInterviewCall implements InterviewManagerCommand<Interview> {
    private final int interviewID;
    private final Map<String, Object> payload;
    private final InterviewRepository interviewRepository;

    public EvaluateInterviewCall(int interviewID, Map<String, Object> payload, InterviewRepository interviewRepository) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.interviewRepository = interviewRepository;
    }

    @Override
    public Interview execute() {
        Interview interview = interviewRepository.getInterviewById(interviewID);
        if (interview == null) {
            throw new NotFoundException("Interview with ID " + interviewID + " not found");
        }
        if(interview.getStatus()!= InterviewStatusEnum.submitted){
            throw new BadInputException("Interview is not in submitted status");
        }
        inputPayloadValidation();
        interview.setEvaluation((Integer) payload.get("evaluation"));
        return interviewRepository.save(interview);
    }

    private void inputPayloadValidation() {
        if(payload.get("company_id")==null){
            throw new BadInputException("Company id not found");
        }
        if(payload.get("evaluation")==null){
            throw new BadInputException("Evaluation not found");
        }
        if(payload.get("status")==null){
            throw new BadInputException("Status not found");
        }
        try {
            InterviewStatusEnum status = InterviewStatusEnum.valueOf((String) payload.get("status"));
            if (status != InterviewStatusEnum.passed && status != InterviewStatusEnum.failed) {
                throw new BadInputException("Status must be passed or failed");
            }
        } catch (IllegalArgumentException e) {
            throw new BadInputException("Invalid status value");
        }
        if((Integer) payload.get("evaluation")<0 || (Integer) payload.get("evaluation")>5){
            throw new BadInputException("Evaluation must be between 0 and 5");
        }
    }

}
