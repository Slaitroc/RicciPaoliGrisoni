package click.studentandcompanies.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//The entity represents the table in the database. Each class represents a table in the database, each field in the class represents a column in the table.
// Can be created directly by the JPA BUDDY when the database is connected and the table is created by clicking new->JPA Entity from the database.
@Entity
public class University {
    @Id
    @Column(name = "universityID", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "country", nullable = false, length = 2)
    private String country;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}