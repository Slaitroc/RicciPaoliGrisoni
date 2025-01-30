package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.CommunicationTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "communication")
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "internship_offer_id", nullable = false)
    private InternshipOffer internshipOffer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "communication_type", nullable = false)
    private CommunicationTypeEnum communicationType;

    public Communication() {
        //empty constructor, required by JPA
    }

    public Communication(Student student, InternshipOffer internshipOffer, University university, String title, CommunicationTypeEnum communicationType) {
        this.student = student;
        this.internshipOffer = internshipOffer;
        this.university = university;
        this.title = title;
        this.communicationType = communicationType;
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

    public InternshipOffer getInternshipOffer() {
        return internshipOffer;
    }

    public void setInternshipOffer(InternshipOffer internshipOffer) {
        this.internshipOffer = internshipOffer;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CommunicationTypeEnum getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(CommunicationTypeEnum communicationType) {
        this.communicationType = communicationType;
    }

}