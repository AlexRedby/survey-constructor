package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.Survey;

import java.util.List;

public interface ResponseSurveyDao {
    ResponseSurvey save(ResponseSurvey responseSurvey);
    List<ResponseSurvey> findAllBySurvey(Survey survey);
}
