package click.studentandcompanies.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "interview_quiz")
public class InterviewQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "answer1", nullable = false)
    private String answer1;

    @NotNull
    @Lob
    @Column(name = "answer2", nullable = false)
    private String answer2;

    @NotNull
    @Lob
    @Column(name = "answer3", nullable = false)
    private String answer3;

    @NotNull
    @Column(name = "answer4", nullable = false)
    private String answer4;

    @NotNull
    @Lob
    @Column(name = "answer5", nullable = false)
    private String answer5;

    @NotNull
    @Column(name = "answer6", nullable = false)
    private String answer6;

    @Column(name = "evaluation")
    private Integer evaluation;

    @OneToOne(mappedBy = "interviewQuiz")
    private Interview interview;

    public InterviewQuiz() {
        //empty constructor required by JPA
    }

    public InterviewQuiz(String answer1, String answer2, String answer3, String answer4, String answer5, String answer6) {
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.answer5 = answer5;
        this.answer6 = answer6;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Interview getInterview() {
        return interview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public String getAnswer6() {
        return answer6;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

}