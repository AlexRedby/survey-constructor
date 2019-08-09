package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

public interface AnswerOptionDao {
    AnswerOption save(AnswerOption option);
    Iterable<AnswerOption> findAllByQuestion(SurveyQuestion surveyQuestion);
}
