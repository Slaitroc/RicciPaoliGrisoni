package click.studentandcompanies.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Size(max = 255)
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "enrolled_in_uni_id")
    private Integer enrolledInUniId;

    @Column(name = "vat_number")
    private Integer vatNumber;

    @Size(max = 3)
    @Column(name = "country", length = 3)
    private String country;

    @NotNull
    @Column(name = "validate", nullable = false)
    private Boolean validate = false;

    public Account() {
        // Empty constructor required by JPA
    }

    public Account(String uuid, String name, String email, Integer enrolledInUniId, Integer vatNumber, String country, Boolean validate) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.enrolledInUniId = enrolledInUniId;
        this.vatNumber = vatNumber;
        this.country = country;
        this.validate = validate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Integer getEnrolledInUniId() {
        return enrolledInUniId;
    }

    public void setEnrolledInUniId(Integer enrolledInUniId) {
        this.enrolledInUniId = enrolledInUniId;
    }

    public Integer getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(Integer vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

}