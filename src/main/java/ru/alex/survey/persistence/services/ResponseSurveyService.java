package ru.alex.survey.persistence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.repositories.jdbc.ResponseSurveyDao;
import ru.alex.survey.persistence.repositories.jdbc.SurveyDao;
import ru.alex.survey.persistence.repositories.jdbc.UserAnswerDao;

import java.util.List;

@Service
public class ResponseSurveyService {
    private ResponseSurveyDao responseSurveyDao;
    private UserAnswerDao userAnswerDao;
    private SurveyDao surveyDao;

    @Autowired
    public ResponseSurveyService(
            ResponseSurveyDao responseSurveyDao,
            UserAnswerDao userAnswerDao,
            SurveyDao surveyDao
    ) {
        this.responseSurveyDao = responseSurveyDao;
        this.userAnswerDao = userAnswerDao;
        this.surveyDao = surveyDao;
    }

    @Transactional
    public ResponseSurvey save(ResponseSurvey responseSurvey) {
        responseSurvey = responseSurveyDao.save(responseSurvey);
        responseSurvey.getAnswers().forEach(a -> userAnswerDao.save(a));

        surveyDao.incrementResponseCountForSurvey(responseSurvey.getSurvey());

        return responseSurvey;
    }

    @Transactional
    public ResponseSurvey saveWithException(ResponseSurvey responseSurvey) {
        responseSurvey = responseSurveyDao.save(responseSurvey);
        responseSurvey.getAnswers().forEach(a -> userAnswerDao.save(a));

        if(responseSurvey.equals(responseSurvey))
            throw new RuntimeException("Test Exception");

        surveyDao.incrementResponseCountForSurvey(responseSurvey.getSurvey());

        return responseSurvey;
    }

    @Transactional
    public List<ResponseSurvey> findAllBySurvey(Survey survey) {
        List<ResponseSurvey> responseSurveys = responseSurveyDao.findAllBySurvey(survey);
        responseSurveys.forEach(rs -> rs.setAnswers(userAnswerDao.findAllByResponseSurvey(rs)));

        return responseSurveys;
    }
}
