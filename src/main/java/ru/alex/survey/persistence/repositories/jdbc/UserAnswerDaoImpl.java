package ru.alex.survey.persistence.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.response.SelectedAnswer;
import ru.alex.survey.persistence.models.response.StringAnswer;
import ru.alex.survey.persistence.models.response.UserAnswer;
import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Primary
@Repository
public class UserAnswerDaoImpl implements UserAnswerDao {
    JdbcTemplate jdbc;

    @Autowired
    public UserAnswerDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public UserAnswer save(UserAnswer userAnswer) {
        long userAnswerId = saveUserAnswer(userAnswer);
        userAnswer.setId(userAnswerId);
        return userAnswer;
    }

    @Override
    public List<UserAnswer> findAllByResponseSurvey(ResponseSurvey responseSurvey) {
        List<UserAnswer> answers = jdbc.query(
                "select id, question_id, answer_id from Selected_Answers where response_survey_id = ?",
                this::mapRowToSelectedAnswerWithoutResponseSurvey,
                responseSurvey.getId()
        );
        answers.addAll(jdbc.query(
                "select id, question_id, answer from String_Answers where response_survey_id = ?",
                this::mapRowToStringAnswerWithoutResponseSurvey,
                responseSurvey.getId()
        ));

        answers.forEach(rs -> rs.setResponseSurvey(responseSurvey));

        return answers;
    }

    private long saveUserAnswer(UserAnswer userAnswer) {
        PreparedStatementCreatorFactory pscf = null;
        Object answer = null;
        if(userAnswer instanceof StringAnswer) {
            // "insert into String_Answers (id, question_id, response_survey_id, answer) values (answer_seq.nextval, ?, ?, ?)",
            pscf = new PreparedStatementCreatorFactory(
                    "insert into String_Answers (question_id, response_survey_id, answer) " +
                            "values (?, ?, ?)",
                Types.BIGINT, Types.BIGINT, Types.VARCHAR
            );
            answer = userAnswer.getAnswer();
        } else {
            pscf = new PreparedStatementCreatorFactory(
                "insert into Selected_Answers (question_id, response_survey_id, answer_id) " +
                        "values (?, ?, ?)",
                Types.BIGINT, Types.BIGINT, Types.BIGINT
            );
            answer = ((AnswerOption) userAnswer.getAnswer()).getId();
        }
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(new Object[] {
                userAnswer.getQuestion().getId(),
                userAnswer.getResponseSurvey().getId(),
                answer
        });

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private UserAnswer mapRowToSelectedAnswerWithoutResponseSurvey(ResultSet rs, int rowNum) throws SQLException {
        SelectedAnswer answer = new SelectedAnswer();
        // TODO: использовать AnswerOptionDAO и SurveyQuestionDAO?
        // TODO: или запросить answerOption и Question в одном select?
        AnswerOption ao = new AnswerOption();
        ao.setId(rs.getLong("answer_id"));
        SurveyQuestion question = new SurveyQuestion();
        question.setId(rs.getLong("question_id"));

        answer.setId(rs.getLong("id"));
        answer.setAnswer(ao);
        answer.setQuestion(question);

        return answer;
    }

    private UserAnswer mapRowToStringAnswerWithoutResponseSurvey(ResultSet rs, int rowNum) throws SQLException {
        StringAnswer answer = new StringAnswer();

        SurveyQuestion question = new SurveyQuestion();
        question.setId(rs.getLong("question_id"));

        answer.setId(rs.getLong("id"));
        answer.setAnswer(rs.getString("answer"));
        answer.setQuestion(question);

        return answer;
    }
}
