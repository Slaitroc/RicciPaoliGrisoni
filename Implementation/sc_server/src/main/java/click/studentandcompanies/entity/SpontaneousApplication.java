package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.SponatenousApplicationStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "spontaneous_application")
public class SpontaneousApplication {
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

    @Lob
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SponatenousApplicationStatusEnum status;

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

    public SponatenousApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SponatenousApplicationStatusEnum status) {
        this.status = status;
    }

}