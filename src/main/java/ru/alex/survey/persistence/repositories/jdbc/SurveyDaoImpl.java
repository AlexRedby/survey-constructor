package ru.alex.survey.persistence.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.survey.Survey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class SurveyDaoImpl implements SurveyDao {

    JdbcTemplate jdbc;

    @Autowired
    public SurveyDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Survey save(Survey survey) {
        long surveyId = saveSurvey(survey);
        survey.setId(surveyId);
        return survey;
    }

    @Override
    public void incrementResponseCountForSurvey(Survey survey) {
        jdbc.update("update Surveys set response_count = response_count + 1 where ID = ?", survey.getId());
    }

    @Override
    public List<Survey> findAll() {
        return jdbc.query("select id, name, response_count from Surveys", this::mapRowToSurvey);
    }

    @Override
    public Optional<Survey> findById(Long id) {
        return Optional.ofNullable(jdbc.queryForObject(
                "select id, name, response_count from Surveys where id = ?",
                this::mapRowToSurvey,
                id
        ));
    }

    private long saveSurvey(Survey survey) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Surveys (name, response_count) values (?, ?)",
                Types.VARCHAR, Types.INTEGER
        );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(new Object[] {
                survey.getName(),
                survey.getResponseCount()
        });

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private Survey mapRowToSurvey(ResultSet rs, int rowNum) throws SQLException {
        Survey survey = new Survey();

        survey.setId(rs.getLong("id"));
        survey.setName(rs.getString("name"));
        survey.setResponseCount(rs.getInt("response_count"));

        return survey;
    }
}
