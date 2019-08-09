package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

public interface SurveyQuestionDao {
    SurveyQuestion save(SurveyQuestion question);
    Iterable<SurveyQuestion> findAllBySurvey(Survey survey);
}
