package ru.alex.survey.persistence.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class AnswerOptionDaoImpl implements AnswerOptionDao {
    JdbcTemplate jdbc;

    @Autowired
    public AnswerOptionDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public AnswerOption save(AnswerOption option) {
        long optionId = saveAnswerOption(option);
        option.setId(optionId);
        return option;
    }

    @Override
    public Iterable<AnswerOption> findAllByQuestion(SurveyQuestion surveyQuestion) {
        Iterable<AnswerOption> options = jdbc.query(
                "select id, text from Answer_Options where question_id = ?",
                this::mapRowToAnswerOptionsWithoutQuestion,
                surveyQuestion.getId()
        );

        options.forEach(q -> q.setQuestion(surveyQuestion));

        return options;
    }

    private long saveAnswerOption(AnswerOption option) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Answer_Options (text, question_id) values (?, ?)",
                Types.VARCHAR, Types.BIGINT
        );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(new Object[] {
                option.getText(),
                option.getQuestion().getId()
        });

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    private AnswerOption mapRowToAnswerOptionsWithoutQuestion(ResultSet rs, int rowNum) throws SQLException {
        AnswerOption option = new AnswerOption();

        option.setId(rs.getLong("id"));
        option.setText(rs.getString("text"));

        return option;
    }
}
