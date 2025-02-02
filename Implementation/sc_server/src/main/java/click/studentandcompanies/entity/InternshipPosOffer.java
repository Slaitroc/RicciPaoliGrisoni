package click.studentandcompanies.entity;

import click.studentandcompanies.entity.dbEnum.InternshipPosOfferStatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "internship_pos_offer")
public class InternshipPosOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InternshipPosOfferStatusEnum status;

    @OneToOne(mappedBy = "internshipPosOffer")
    private Interview interview;

    public InternshipPosOffer() {
        //empty constructor required by JPA
    }

    public InternshipPosOffer(InternshipPosOfferStatusEnum status, Interview interview) {
        this.status = status;
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InternshipPosOfferStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InternshipPosOfferStatusEnum status) {
        this.status = status;
    }

}