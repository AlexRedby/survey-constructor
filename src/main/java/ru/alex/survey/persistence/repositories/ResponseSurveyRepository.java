package ru.alex.survey.persistence.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.Survey;

@Repository
public interface ResponseSurveyRepository extends CrudRepository<ResponseSurvey, Long> {

    @Query("select distinct rs from ResponseSurvey rs inner join fetch rs.answers where rs.survey = :survey")
    Iterable<ResponseSurvey> findAllBySurveyId(@Param("survey") Survey survey);
}
