package ru.alex.survey.persistence.models.survey;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class SurveyQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(nullable = false)
    private QuestionType type;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "question")
    private List<AnswerOption> answerOptions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        answerOptions.forEach(ao -> ao.setQuestion(this));
        this.answerOptions = answerOptions;
    }
}
