package click.studentandcompanies.entityManager.interviewManager.POST;

import click.studentandcompanies.entity.InternshipPosOffer;
import click.studentandcompanies.entity.Interview;
import click.studentandcompanies.entity.InterviewQuiz;
import click.studentandcompanies.entity.dbEnum.InternshipPosOfferStatusEnum;
import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import click.studentandcompanies.entityManager.UserManager;
import click.studentandcompanies.entityManager.interviewManager.InterviewManagerCommand;
import click.studentandcompanies.entityRepository.InternshipPosOfferRepository;
import click.studentandcompanies.entityRepository.InterviewQuizRepository;
import click.studentandcompanies.entityRepository.InterviewRepository;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import click.studentandcompanies.utils.exception.WrongStateException;

import java.util.Map;

public class EvaluateInterviewCall implements InterviewManagerCommand<Interview> {
    private final int interviewID;
    private final Map<String, Object> payload;
    private final InterviewRepository interviewRepository;
    private final InterviewQuizRepository interviewQuizRepository;
    private final InternshipPosOfferRepository internshipPosOfferRepository;
    private final UserManager userManager;

    public EvaluateInterviewCall(int interviewID, Map<String, Object> payload, InterviewRepository interviewRepository, InterviewQuizRepository interviewQuizRepository, InternshipPosOfferRepository internshipPosOfferRepository, UserManager userManager) {
        this.interviewID = interviewID;
        this.payload = payload;
        this.interviewRepository = interviewRepository;
        this.interviewQuizRepository = interviewQuizRepository;
        this.internshipPosOfferRepository = internshipPosOfferRepository;
        this.userManager = userManager;
    }

    @Override
    public Interview execute() {
        Interview interview = interviewRepository.getInterviewById(interviewID);
        if (interview == null) {
            throw new NotFoundException("Interview with ID " + interviewID + " not found");
        }
        if(!interview.getHasAnswered()){
            throw new WrongStateException("Answer not received yet");
        }
        if(interview.getStatus() != InterviewStatusEnum.submitted){
            System.out.println(interview.getStatus());
            throw new WrongStateException("Interview is not in submitted status");
        }
        inputPayloadValidation(payload);
        InterviewQuiz interviewQuiz = interview.getInterviewQuiz();
        if(interviewQuiz==null){
            throw new NotFoundException("Interview quiz not found");
        }
        Integer evaluation = (Integer) payload.get("evaluation");
        interviewQuizRepository.save(interviewQuiz);
        if(evaluation<=2) {
            interview.setStatus(InterviewStatusEnum.failed);
        }else{
            InternshipPosOffer internshipPosOffer = new InternshipPosOffer(InternshipPosOfferStatusEnum.pending, interview);
            internshipPosOfferRepository.save(internshipPosOffer);
            //todo check if i'm not violating transactional integrity
            interviewQuiz.setEvaluation(evaluation);
            interview.setStatus(InterviewStatusEnum.passed);
            interview.setInternshipPosOffer(internshipPosOffer);
        }
        return interviewRepository.save(interview);
    }

    private void inputPayloadValidation(Map<String, Object> payload) {
        if(payload.get("company_id")==null){
            throw new BadInputException("Company id not found");
        }
        if(userManager.getCompanyById((String) payload.get("company_id"))==null){
            throw new NotFoundException("Company not found");
        }
        if(payload.get("evaluation")==null){
            throw new BadInputException("Evaluation not found");
        }
        if((Integer) payload.get("evaluation")<0 || (Integer) payload.get("evaluation")>5){
            throw new BadInputException("Evaluation must be between 0 and 5");
        }
    }

}
