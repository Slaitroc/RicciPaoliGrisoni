package click.studentandcompanies.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "university")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class University {
    @Id
    @Column(name = "university_id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "country", nullable = false, length = 2)
    private String country;

    @Column(name = "vat_number", nullable = false)
    private Integer vatNumber;

    @Lob
    @Column(name = "uni_desc")
    private String uniDesc;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public University() {
        //empty constructor required by JPA
    }

    public University(String id, String name, String email, String country, Integer vatNumber, String uniDesc, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.country = country;
        this.vatNumber = vatNumber;
        this.uniDesc = uniDesc;
        this.location = location;
    }

    public String getUniDesc() {
        return uniDesc;
    }

    public void setUniDesc(String uniDesc) {
        this.uniDesc = uniDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(Integer vatNumber) {
        this.vatNumber = vatNumber;
    }

}