package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;
import ru.alex.survey.persistence.repositories.jdbc.AnswerOptionDao;
import ru.alex.survey.persistence.repositories.jdbc.SurveyDao;
import ru.alex.survey.persistence.repositories.jdbc.SurveyQuestionDao;

@Controller
@RequestMapping("/constructor")
public class SurveyConstructorController {

    /*SurveyRepository surveyRepository;

        @Autowired
    public SurveyConstructorController(SurveyRepository surveyRepository) {
        super();
        this.surveyRepository = surveyRepository;
    }*/

    private SurveyDao surveyDao;
    private SurveyQuestionDao surveyQuestionDao;
    private AnswerOptionDao answerOptionDao;

    @Autowired
    public SurveyConstructorController(SurveyDao surveyDao,
                                       SurveyQuestionDao surveyQuestionDao,
                                       AnswerOptionDao answerOptionDao
    ) {
        this.surveyDao = surveyDao;
        this.surveyQuestionDao = surveyQuestionDao;
        this.answerOptionDao = answerOptionDao;
    }

    @GetMapping
    public String getSurveyConstructor(Model m) {
        m.addAttribute("question", new SurveyQuestion());
        return "surveyConstructor";
    }

    @PostMapping(consumes = "application/json")
    public String saveSurvey(@RequestBody Survey survey) {
        //System.out.println(survey);

        // JPA
        //surveyRepository.save(survey);

        // JDBC
        survey = surveyDao.save(survey);
        survey.getQuestions().forEach(q -> {
            q = surveyQuestionDao.save(q);
            if(q.getType() == QuestionType.SINGLE_CHOICE || q.getType() == QuestionType.MULTIPLE_CHOICE)
                q.getAnswerOptions().forEach(ao -> ao = answerOptionDao.save(ao));
        });

        return "redirect:/";
    }
}
