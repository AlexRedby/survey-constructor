package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.response.UserAnswer;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.repositories.jdbc.*;
import ru.alex.survey.persistence.services.ResponseSurveyService;
import ru.alex.survey.persistence.services.SurveyService;
import java.util.Optional;

@Controller
@RequestMapping("/survey")
public class SurveyPlayerController {
    private SurveyService surveyService;
    private ResponseSurveyService responseSurveyService;

    @Autowired
    public SurveyPlayerController(SurveyService surveyService, ResponseSurveyService responseSurveyService) {
        this.surveyService = surveyService;
        this.responseSurveyService = responseSurveyService;
    }

    @GetMapping("/{id}")
    public String player(@PathVariable Long id, Model m) {
        Optional<Survey> survey = surveyService.findById(id);

        if(survey.isPresent()) {
            m.addAttribute("survey", survey.get());
            m.addAttribute("responseSurvey", new ResponseSurvey());
            return "player";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/{id}", consumes = "application/json")
    public String saveResult(@PathVariable Long id, @RequestBody ResponseSurvey responseSurvey) {
        //responseSurveyService.saveWithException(responseSurvey);
        responseSurveyService.save(responseSurvey);

        return "redirect:/";
    }

    @GetMapping(value = "/test/{id}", produces = "application/json")
    @ResponseBody
    public Iterable<ResponseSurvey> getResponseSurveys(@PathVariable Long id) {
        Survey survey = new Survey();
        survey.setId(id);

        return responseSurveyService.findAllBySurvey(survey);
    }
}
