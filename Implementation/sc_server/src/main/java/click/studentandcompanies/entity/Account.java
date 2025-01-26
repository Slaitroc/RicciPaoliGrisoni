package click.studentandcompanies.entity;

import click.studentandcompanies.utils.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Size(max = 255)
    @Column(name = "user_id", nullable = false)
    private String userID;

    @NotNull
    @Lob
    @Column(name = "user_type", nullable = false)
    private String userType;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "surname")
    private String surname;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "uni_vat")
    private Integer uniVat;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Size(max = 3)
    @NotNull
    @Column(name = "country", nullable = false, length = 3)
    private String country;

    @Column(name = "vat_number")
    private Integer vatNumber;

    @Lob
    @Column(name = "uni_desc")
    private String uniDesc;

    @NotNull
    @Column(name = "validate", nullable = false)
    private Boolean validate = false;

    public Account() {
        //empty constructor required by JPA
    }

    public Account(String userId, String name, String email, String country, UserType type, Boolean validated, String surname, Integer uniVat, LocalDate birthDate, Integer vatNumber, String uniDescription) {
        this.userID = userId;
        this.name = name;
        this.email = email;
        this.country = country;
        this.userType = type.toString();
        this.validate = validated;
        this.surname = surname;
        this.uniVat = uniVat;
        this.birthDate = LocalDate.parse(birthDate.toString());
        this.vatNumber = vatNumber;
        this.uniDesc = uniDescription;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUniVat() {
        return uniVat;
    }

    public void setUniVat(Integer uniVat) {
        this.uniVat = uniVat;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public String getUniDesc() {
        return uniDesc;
    }

    public void setUniDesc(String uniDesc) {
        this.uniDesc = uniDesc;
    }

    public Boolean getValidate() {
        return validate;
    }

    public void setValidate(Boolean validate) {
        this.validate = validate;
    }

}