package ru.alex.survey.persistence.repositories.jdbc;

import ru.alex.survey.persistence.models.survey.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyDao {
    Survey save(Survey survey);
    void incrementResponseCountForSurvey(Survey survey);
    List<Survey> findAll();
    Optional<Survey> findById(Long id);
}
