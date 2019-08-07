package ru.alex.survey.persistence.models.response;

import lombok.Data;
import lombok.ToString;
import ru.alex.survey.persistence.models.survey.AnswerOption;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@ToString(callSuper = true)
public class SelectedAnswer extends UserAnswer<AnswerOption> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_id", nullable = false)
    private AnswerOption answer;
    //private List<AnswerOption> answer;

    @Override
    public AnswerOption getAnswer() {
        return answer;
    }

    @Override
    public void setAnswer(AnswerOption answer) {
        this.answer = answer;
    }
}
