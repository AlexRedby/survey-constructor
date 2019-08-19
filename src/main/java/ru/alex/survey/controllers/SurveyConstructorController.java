package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;
import ru.alex.survey.persistence.services.SurveyService;

@Controller
@RequestMapping("/constructor")
public class SurveyConstructorController {
    private SurveyService surveyService;

    @Autowired
    public SurveyConstructorController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping
    public String getSurveyConstructor(Model m) {
        m.addAttribute("question", new SurveyQuestion());
        return "surveyConstructor";
    }

    @PostMapping(consumes = "application/json")
    public String saveSurvey(@RequestBody Survey survey) {
        surveyService.save(survey);

        return "redirect:/";
    }
}
