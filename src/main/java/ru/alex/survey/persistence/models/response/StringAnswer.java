package ru.alex.survey.persistence.models.response;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@ToString(callSuper = true)
@Table(name = "STRING_ANSWERS")
public class StringAnswer extends UserAnswer<String> {

    @Column(nullable = false)
    private String answer;

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
