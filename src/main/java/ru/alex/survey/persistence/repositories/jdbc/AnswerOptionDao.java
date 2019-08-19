package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

import java.util.List;

public interface AnswerOptionDao {
    AnswerOption save(AnswerOption option);
    List<AnswerOption> findAllByQuestion(SurveyQuestion surveyQuestion);
}
