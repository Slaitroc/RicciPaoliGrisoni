package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.SpontaneousApplicationStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "spontaneous_application")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    private SpontaneousApplicationStatusEnum status;

    public SpontaneousApplication() {
        //Empty constructor required by JPA
    }

    public SpontaneousApplication(Student student, InternshipOffer internshipOffer, SpontaneousApplicationStatusEnum status) {
        this.student = student;
        this.internshipOffer = internshipOffer;
        this.status = status;
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

    public SpontaneousApplicationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SpontaneousApplicationStatusEnum status) {
        this.status = status;
    }

}