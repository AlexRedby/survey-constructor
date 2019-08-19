package ru.alex.survey.persistence.models.survey;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "SURVEYS")
@Data
public class Survey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "response_count", nullable = false)
    private int responseCount;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "survey")
    private List<SurveyQuestion> questions;

    public Survey() {
        id = null;
        name = null;
        this.responseCount = 0;
        questions = null;
    }

    public void setQuestions(List<SurveyQuestion> questions) {
        questions.forEach(q -> q.setSurvey(this));
        this.questions = questions;
    }
}
