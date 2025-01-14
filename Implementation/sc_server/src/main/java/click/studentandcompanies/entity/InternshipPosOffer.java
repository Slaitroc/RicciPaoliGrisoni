package click.studentandcompanies.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "internship_pos_offer")
public class InternshipPosOffer {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "acceptance")
    private Boolean acceptance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(Boolean acceptance) {
        this.acceptance = acceptance;
    }

}