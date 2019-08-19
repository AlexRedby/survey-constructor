package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.response.UserAnswer;

import java.util.List;

public interface UserAnswerDao {
    UserAnswer save(UserAnswer userAnswer);
    List<UserAnswer> findAllByResponseSurvey(ResponseSurvey responseSurvey);
}
