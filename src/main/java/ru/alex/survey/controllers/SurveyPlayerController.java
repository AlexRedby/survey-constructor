package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.response.UserAnswer;
import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;
import ru.alex.survey.persistence.repositories.ResponseSurveyRepository;
import ru.alex.survey.persistence.repositories.SurveyRepository;
import ru.alex.survey.persistence.repositories.jdbc.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/survey")
public class SurveyPlayerController {

    /*private SurveyRepository surveyRepository;
    private ResponseSurveyRepository responseSurveyRepository;

    @Autowired
    public SurveyPlayerController(SurveyRepository surveyRepository,
                                  ResponseSurveyRepository responseSurveyRepository
    ) {
        this.surveyRepository = surveyRepository;
        this.responseSurveyRepository = responseSurveyRepository;
    }*/

    private SurveyDao surveyDao;
    private SurveyQuestionDao surveyQuestionDao;
    private AnswerOptionDao answerOptionDao;

    private ResponseSurveyDao responseSurveyDao;
    private UserAnswerDao userAnswerDao;

    @Autowired
    public SurveyPlayerController(
            SurveyDao surveyDao,
            SurveyQuestionDao surveyQuestionDao,
            AnswerOptionDao answerOptionDao,
            ResponseSurveyDao responseSurveyDao,
            UserAnswerDao userAnswerDao
    ) {
        this.surveyDao = surveyDao;
        this.surveyQuestionDao = surveyQuestionDao;
        this.answerOptionDao = answerOptionDao;
        this.responseSurveyDao = responseSurveyDao;
        this.userAnswerDao = userAnswerDao;
    }

    @GetMapping("/{id}")
    public String player(@PathVariable Long id, Model m) {
        // JPA
        //Optional<Survey> oSurvey = surveyRepository.findById(id);
        // JDBC
        Optional<Survey> oSurvey = surveyDao.findById(id);

        if(oSurvey.isPresent()) {
            Survey survey = oSurvey.get();

            // JDBC
            // Getting questions for survey
            survey.setQuestions((List<SurveyQuestion>) surveyQuestionDao.findAllBySurvey(survey));
            // Getting answers options for all choiceable questions
            survey.getQuestions().forEach(q -> {
                if(q.getType().getType().equals(QuestionType.Constants.CHOICE_TYPE))
                    q.setAnswerOptions((List<AnswerOption>) answerOptionDao.findAllByQuestion(q));
            });

            m.addAttribute("survey", survey);
            m.addAttribute("responseSurvey", new ResponseSurvey());
            return "player";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/{id}", consumes = "application/json")
    public String saveResult(@PathVariable Long id, @RequestBody ResponseSurvey responseSurvey) {
        //System.out.println("This is success: " + responseSurvey);
        //System.out.println("Answers: " + Arrays.toString(responseSurvey.getAnswers().toArray()));

        // JPA
        //responseSurveyRepository.save(responseSurvey);
        // JDBC
        responseSurvey = responseSurveyDao.save(responseSurvey);
        responseSurvey.getAnswers().forEach(a -> userAnswerDao.save(a));

        return "redirect:/";
    }

    @GetMapping(value = "/test/{id}", produces = "application/json")
    @ResponseBody
    public Iterable<ResponseSurvey> getResponseSurveys(@PathVariable Long id) {
        Survey survey = new Survey();
        survey.setId(id);

        // JPA
        //return responseSurveyRepository.findAllBySurvey(survey);
        // JDBC
        Iterable<ResponseSurvey> responseSurveys = responseSurveyDao.findAllBySurvey(survey);
        responseSurveys.forEach(rs -> rs.setAnswers((List<UserAnswer>) userAnswerDao.findAllByResponseSurvey(rs)));
        return responseSurveys;
    }
}
