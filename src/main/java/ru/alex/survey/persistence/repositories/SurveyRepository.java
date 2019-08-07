package ru.alex.survey.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.survey.Survey;

@Repository
public interface SurveyRepository extends CrudRepository<Survey, Long> {
}
