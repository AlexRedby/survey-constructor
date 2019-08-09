package ru.alex.survey.persistence.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class SurveyQuestionDaoImpl implements SurveyQuestionDao {
    JdbcTemplate jdbc;

    @Autowired
    public SurveyQuestionDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public SurveyQuestion save(SurveyQuestion question) {
        long questionId = saveQuestion(question);
        question.setId(questionId);
        return question;
    }

    @Override
    public Iterable<SurveyQuestion> findAllBySurvey(Survey survey) {
        Iterable<SurveyQuestion> questions = jdbc.query(
                "select id, text, type from Questions where survey_id = ?",
                this::mapRowToQuestionWithoutSurvey,
                survey.getId()
        );

        questions.forEach(q -> q.setSurvey(survey));

        return questions;
    }

    private long saveQuestion(SurveyQuestion question) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Questions (text, type, survey_id) values (?, ?, ?)",
                Types.VARCHAR, Types.INTEGER, Types.BIGINT
        );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(new Object[] {
                question.getText(),
                question.getType().ordinal(),
                question.getSurvey().getId()

        });

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private SurveyQuestion mapRowToQuestionWithoutSurvey(ResultSet rs, int rowNum) throws SQLException {
        SurveyQuestion question = new SurveyQuestion();

        question.setId(rs.getLong("id"));
        question.setText(rs.getString("text"));
        question.setType(QuestionType.values()[rs.getInt("type")]);

        return question;
    }
}
