package ru.alex.survey.persistence.models.survey;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "SURVEYS")
@Data
@NoArgsConstructor
public class Survey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "survey")
    private List<SurveyQuestion> questions;

    public void setQuestions(List<SurveyQuestion> questions) {
        questions.forEach(q -> q.setSurvey(this));
        this.questions = questions;
    }
}
