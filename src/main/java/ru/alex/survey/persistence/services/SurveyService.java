package ru.alex.survey.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;
import ru.alex.survey.persistence.repositories.jdbc.AnswerOptionDao;
import ru.alex.survey.persistence.repositories.jdbc.SurveyDao;
import ru.alex.survey.persistence.repositories.jdbc.SurveyQuestionDao;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SurveyService {
    private SurveyDao surveyDao;
    private SurveyQuestionDao questionDao;
    private AnswerOptionDao answerOptionDao;

    @Autowired
    public SurveyService(SurveyDao surveyDao, SurveyQuestionDao questionDao, AnswerOptionDao answerOptionDao) {
        this.surveyDao = surveyDao;
        this.questionDao = questionDao;
        this.answerOptionDao = answerOptionDao;
    }

    public Survey save(Survey survey) {
        survey = surveyDao.save(survey);
        survey.getQuestions().forEach(q -> {
            q = questionDao.save(q);
            if(q.getType() == QuestionType.SINGLE_CHOICE || q.getType() == QuestionType.MULTIPLE_CHOICE)
                q.getAnswerOptions().forEach(ao -> ao = answerOptionDao.save(ao));
        });

        return survey;
    }

    public Optional<Survey> findById(Long id) {
        Optional<Survey> oSurvey = surveyDao.findById(id);

        // Если анкета найдена, то подгружаем к ней вопросы и варианты ответов
        if(oSurvey.isPresent()) {
            Survey survey = oSurvey.get();

            // Getting questions for survey
            survey.setQuestions(questionDao.findAllBySurvey(survey));
            // Getting answers options for all choiceable questions
            survey.getQuestions().forEach(q -> {
                if(q.getType().getType().equals(QuestionType.Constants.CHOICE_TYPE))
                    q.setAnswerOptions(answerOptionDao.findAllByQuestion(q));
            });
        }

        return oSurvey;
    }

    public List<Survey> findAll() {
        return surveyDao.findAll();
    }
}
