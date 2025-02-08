package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recommendation_id", nullable = false)
    private Recommendation recommendation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "participant_type", nullable = false)
    private ParticipantTypeEnum participantType;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @NotNull
    @Column(name = "upload_time", nullable = false)
    private Instant uploadTime;

    public Instant getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Feedback() {
        // default constructor required by JPA
    }

    public Feedback(Recommendation recommendation, ParticipantTypeEnum participantType, Integer rating, Instant uploadTime) {
        this.recommendation = recommendation;
        this.participantType = participantType;
        this.rating = rating;
        this.uploadTime = uploadTime;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ParticipantTypeEnum getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantTypeEnum participantTyp) {
        this.participantType = participantTyp;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}