package ru.alex.survey.persistence.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.Survey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class ResponseSurveyDaoImpl implements ResponseSurveyDao {
    JdbcTemplate jdbc;

    @Autowired
    public ResponseSurveyDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public ResponseSurvey save(ResponseSurvey responseSurvey) {
        long responseSurveyId = saveResponseSurvey(responseSurvey);
        responseSurvey.setId(responseSurveyId);
        return responseSurvey;
    }

    @Override
    public Iterable<ResponseSurvey> findAllBySurvey(Survey survey) {
        Iterable<ResponseSurvey> responseSurveys = jdbc.query(
                "select id from Response_Surveys where survey_id = ?",
                this::mapRowToResponseSurveyWithoutSurvey,
                survey.getId()
        );

        responseSurveys.forEach(rs -> rs.setSurvey(survey));

        return responseSurveys;
    }

    private long saveResponseSurvey(ResponseSurvey responseSurvey) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Response_Surveys (survey_id) values (?)",
                Types.BIGINT
        );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(new Object[] {
                responseSurvey.getSurvey().getId()
        });

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private ResponseSurvey mapRowToResponseSurveyWithoutSurvey(ResultSet rs, int rowNum) throws SQLException {
        ResponseSurvey responseSurvey = new ResponseSurvey();

        responseSurvey.setId(rs.getLong("id"));

        return responseSurvey;
    }
}
