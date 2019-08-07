package ru.alex.survey.persistence.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.alex.survey.persistence.models.survey.Survey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"survey"}, allowSetters = true)
public class ResponseSurvey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    //private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "survey_id", nullable = false)
    @JsonProperty("survey")
    private Survey survey;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "responseSurvey")
    private List<UserAnswer> answers;

    public void setAnswers(List<UserAnswer> answers) {
        answers.forEach(q -> q.setResponseSurvey(this));
        this.answers = answers;
    }
}
