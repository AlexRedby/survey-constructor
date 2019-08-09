package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.response.UserAnswer;

public interface UserAnswerDao {
    UserAnswer save(UserAnswer userAnswer);
    Iterable<UserAnswer> findAllByResponseSurvey(ResponseSurvey responseSurvey);
}
