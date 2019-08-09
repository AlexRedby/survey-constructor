package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.Survey;

public interface ResponseSurveyDao {
    ResponseSurvey save(ResponseSurvey responseSurvey);
    Iterable<ResponseSurvey> findAllBySurvey(Survey survey);
}
