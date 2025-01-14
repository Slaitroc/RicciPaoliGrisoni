package click.studentandcompanies.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "interview")
public class Interview {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_template_id", nullable = false)
    private InterviewTemplate interviewTemplate;

    //Note: even if both recommendation and spontaneous_application are optional, one of them must be present.
    //The CHECK is implemented in the database.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spontaneous_application_id")
    private SpontaneousApplication spontaneousApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_pos_offer_id")
    private InternshipPosOffer internshipPosOffer;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}