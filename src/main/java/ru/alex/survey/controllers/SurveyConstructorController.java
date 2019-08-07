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
import ru.alex.survey.persistence.repositories.SurveyRepository;

@Controller
@RequestMapping("/constructor")
public class SurveyConstructorController {

    SurveyRepository surveyRepository;

    @Autowired
    public SurveyConstructorController(SurveyRepository surveyRepository) {
        super();
        this.surveyRepository = surveyRepository;
    }

    @GetMapping
    public String getSurveyConstructor(Model m) {
        m.addAttribute("question", new SurveyQuestion());
        return "surveyConstructor";
    }

    @PostMapping(consumes = "application/json")
    public String saveSurvey(@RequestBody Survey survey) {
        System.out.println(survey);

        surveyRepository.save(survey);

        return "redirect:/";
    }
}
