package click.studentandcompanies.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "cv")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "skills")
    private String skills;

    @Column(name = "work_experiences")
    private String workExperiences;

    @Column(name = "education")
    private String education;

    @Column(name = "project")
    private String project;

    @Column(name = "certifications")
    private String certifications;

    @ColumnDefault("current_timestamp()")
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @Size(max = 255)
    @NotNull
    @Column(name = "spoken_languages", nullable = false)
    private String spokenLanguages;

    @Size(max = 255)
    @Column(name = "contacts")
    private String contacts;

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public Cv(Student student, String skills, String workExperiences, String education, String project, String certifications, Instant updateTime, String spokenLanguages, String contacts) {
        this.student = student;
        this.skills = skills;
        this.workExperiences = workExperiences;
        this.education = education;
        this.project = project;
        this.certifications = certifications;
        this.updateTime = updateTime;
        this.spokenLanguages = spokenLanguages;
        this.contacts = contacts;
    }

    public Cv() {
        //Default constructor required by JPA
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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(String workExperiences) {
        this.workExperiences = workExperiences;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}