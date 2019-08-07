package ru.alex.survey.persistence.models.response;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

import javax.persistence.*;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = false)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SelectedAnswer.class, name = QuestionType.Constants.CHOICE_TYPE),
        @JsonSubTypes.Type(value = StringAnswer.class, name = QuestionType.Constants.INPUT_TYPE)
})
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@JsonIgnoreProperties(value = {"responseSurvey", "question"}, allowSetters = true)
public abstract class UserAnswer<T> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_survey_id", nullable = false)
    @JsonProperty("responseSurvey")
    private ResponseSurvey responseSurvey;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonProperty("question")
    private SurveyQuestion question;

    public abstract T getAnswer();
    public abstract void setAnswer(T answer);
}
