package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.CommunicationTypeEnum;
import click.studentandcompanies.entity.dbEnum.ParticipantTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "communication")
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "participant_type", nullable = false)
    private ParticipantTypeEnum participantType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "internship_pos_off_id", nullable = false)
    private InternshipPosOffer internshipPosOff;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type", nullable = false)
    private CommunicationTypeEnum communicationType;

    @Column(name = "updateTime", nullable = false)
    private Instant updateTime;

    public Instant getUpdateTime() {
        return updateTime;
    }
    public Communication() {
        // Empty constructor required by JPA
    }

    public Communication(Student student, Company company, ParticipantTypeEnum participantType,InternshipPosOffer internshipPosOff, String title, String content, CommunicationTypeEnum communicationType) {
        this.student = student;
        this.company = company;
        this.participantType = participantType;
        this.internshipPosOff = internshipPosOff;
        this.title = title;
        this.content = content;
        this.communicationType = communicationType;
        this.updateTime = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public InternshipPosOffer getInternshipPosOff() {
        return internshipPosOff;
    }

    public void setInternshipPosOff(InternshipPosOffer internshipPosOff) {
        this.internshipPosOff = internshipPosOff;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommunicationTypeEnum getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(CommunicationTypeEnum communicationType) {
        this.communicationType = communicationType;
    }

    public ParticipantTypeEnum getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantTypeEnum participantType) {
        this.participantType = participantType;
    }

}