package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.repositories.SurveyRepository;
import ru.alex.survey.persistence.repositories.jdbc.SurveyDao;

@Controller
@RequestMapping("/")
public class HomeController {

    /*private SurveyRepository surveyRepository;

    @Autowired
    public HomeController(SurveyRepository surveyRepository) {
        super();
        this.surveyRepository = surveyRepository;
    }*/

    private SurveyDao surveyDao;

    @Autowired
    public HomeController(SurveyDao surveyDao) {
        super();
        this.surveyDao = surveyDao;
    }

    @GetMapping
    public String home(Model m) {
        Iterable<Survey> surveys = surveyDao.findAll();

        System.out.println(surveys);
        m.addAttribute("surveys", surveys);

        return "index";
    }
}
