package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.InterviewStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InterviewStatusEnum status;

    @OneToOne
    @JoinColumn(name = "internship_pos_offer_id")
    private InternshipPosOffer internshipPosOffer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spontaneous_application_id")
    private SpontaneousApplication spontaneousApplication;

    @NotNull
    @Column(name = "has_answered", nullable = false)
    private Boolean hasAnswered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_template_id")
    private InterviewTemplate interviewTemplate;

    @OneToOne
    @JoinColumn(name = "interview_quiz_id")
    private InterviewQuiz interviewQuiz;

    public InterviewQuiz getInterviewQuiz() {
        return interviewQuiz;
    }

    public void setInterviewQuiz(InterviewQuiz interviewQuiz) {
        this.interviewQuiz = interviewQuiz;
    }

    public InterviewTemplate getInterviewTemplate() {
        return interviewTemplate;
    }

    public void setInterviewTemplate(InterviewTemplate interviewTemplate) {
        this.interviewTemplate = interviewTemplate;
    }

    public Boolean getHasAnswered() {
        return hasAnswered;
    }

    public void setHasAnswered(Boolean hasAnswered) {
        this.hasAnswered = hasAnswered;
    }

    public Interview() {
        //empty constructor required by JPA
    }

    public Interview(InterviewStatusEnum status, Recommendation recommendation, SpontaneousApplication spontaneousApplication){
        this.status = status;
        this.recommendation = recommendation;
        this.spontaneousApplication = spontaneousApplication;
        this.hasAnswered = false;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


}