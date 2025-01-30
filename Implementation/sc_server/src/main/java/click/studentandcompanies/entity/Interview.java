package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "interview_template_id", nullable = true)
    private InterviewTemplate interviewTemplate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spontaneous_application_id")
    private SpontaneousApplication spontaneousApplication;

    @OneToOne
    @JoinColumn(name = "internship_pos_offer_id")
    private InternshipPosOffer internshipPosOffer;

    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InterviewStatusEnum status;

    @Lob
    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @Column(name="evaluation")
    private Integer evaluation;



    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InterviewTemplate getInterviewTemplate() {
        return interviewTemplate;
    }

    public void setInterviewTemplate(InterviewTemplate interviewTemplate) {
        this.interviewTemplate = interviewTemplate;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public SpontaneousApplication getSpontaneousApplication() {
        return spontaneousApplication;
    }

    public void setSpontaneousApplication(SpontaneousApplication spontaneousApplication) {
        this.spontaneousApplication = spontaneousApplication;
    }

    public InternshipPosOffer getInternshipPosOffer() {
        return internshipPosOffer;
    }

    public void setInternshipPosOffer(InternshipPosOffer internshipPosOffer) {
        this.internshipPosOffer = internshipPosOffer;
    }

    public InterviewStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InterviewStatusEnum status) {
        this.status = status;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

}