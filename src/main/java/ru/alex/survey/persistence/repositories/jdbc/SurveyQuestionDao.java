package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

import java.util.List;

public interface SurveyQuestionDao {
    SurveyQuestion save(SurveyQuestion question);
    List<SurveyQuestion> findAllBySurvey(Survey survey);
}
