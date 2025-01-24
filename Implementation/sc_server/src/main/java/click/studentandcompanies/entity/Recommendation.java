package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.RecommendationStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recommendation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "internship_offer_id", nullable = false)
    private InternshipOffer internshipOffer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cv_id", nullable = false)
    private Cv cv;

    @OneToOne(mappedBy = "recommendation", optional = true)
    private Interview interview;

    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RecommendationStatusEnum status;

    @NotNull
    @Column(name = "score", nullable = false)
    private Float score;

    public Recommendation() {
        //empty constructor require by jpa
    }

    public Recommendation(InternshipOffer internshipOffer, Cv cv, RecommendationStatusEnum status, Float score) {
        this.internshipOffer = internshipOffer;
        this.cv = cv;
        this.status = status;
        this.score = score;
    }
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InternshipOffer getInternshipOffer() {
        return internshipOffer;
    }

    public void setInternshipOffer(InternshipOffer internshipOffer) {
        this.internshipOffer = internshipOffer;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public RecommendationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RecommendationStatusEnum status) {
        this.status = status;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }

}