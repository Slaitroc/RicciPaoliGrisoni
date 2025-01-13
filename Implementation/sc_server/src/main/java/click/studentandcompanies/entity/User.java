package click.studentandcompanies.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
//The entity represents the table in the database. Each class represents a table in the database, each field in the class represents a column in the table.
// Can be created directly by the JPA BUDDY when the database is connected and the table is created by clicking new->JPA Entity from the database.
@Entity
public class User {
    @Id
    @Column(name = "userID", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "email", length = 30)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "universityID")
    private University universityID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public University getUniversityID() {
        return universityID;
    }

    public void setUniversityID(University universityID) {
        this.universityID = universityID;
    }

}