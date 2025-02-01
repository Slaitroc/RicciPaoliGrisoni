package click.studentandcompanies.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "interview_template")
public class InterviewTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "question1", nullable = false)
    private String question1;

    @NotNull
    @Lob
    @Column(name = "question2", nullable = false)
    private String question2;

    @NotNull
    @Lob
    @Column(name = "question3", nullable = false)
    private String question3;

    @NotNull
    @Lob
    @Column(name = "question4", nullable = false)
    private String question4;

    @NotNull
    @Lob
    @Column(name = "question5", nullable = false)
    private String question5;

    @NotNull
    @Lob
    @Column(name = "question6", nullable = false)
    private String question6;

    public InterviewTemplate() {
        //empty constructor required by JPA
    }

    public InterviewTemplate(String question1, String question2, String question3, String question4, String question5, String question6) {
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}