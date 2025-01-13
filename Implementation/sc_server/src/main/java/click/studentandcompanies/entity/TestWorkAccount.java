package click.studentandcompanies.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_work_account")
public class TestWorkAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String role;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "email")
    private Test workEmail;

    public Test getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(Test workEmail) {
        this.workEmail = workEmail;
    }
}