package ru.alex.survey.persistence.models.response;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@ToString(callSuper = true)
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
