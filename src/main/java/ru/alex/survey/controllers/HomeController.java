package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.repositories.jdbc.SurveyDao;
import ru.alex.survey.persistence.services.SurveyService;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private SurveyService surveyService;

    @Autowired
    public HomeController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping
    public String home(Model m) {
        List<Survey> surveys = surveyService.findAll();

        m.addAttribute("surveys", surveys);

        return "index";
    }
}
