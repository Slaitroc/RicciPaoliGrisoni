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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InternshipPosOfferStatusEnum status;

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