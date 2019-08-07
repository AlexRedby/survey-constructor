package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.repositories.ResponseSurveyRepository;
import ru.alex.survey.persistence.repositories.SurveyRepository;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/survey")
public class SurveyPlayerController {

    private SurveyRepository surveyRepository;
    private ResponseSurveyRepository responseSurveyRepository;

    @Autowired
    public SurveyPlayerController(SurveyRepository surveyRepository,
                                  ResponseSurveyRepository responseSurveyRepository
    ) {
        this.surveyRepository = surveyRepository;
        this.responseSurveyRepository = responseSurveyRepository;
    }

    @GetMapping("/{id}")
    public String player(@PathVariable Long id, Model m) {
        Optional<Survey> survey = surveyRepository.findById(id);

        if(survey.isPresent()) {
            m.addAttribute("survey", survey.get());
            m.addAttribute("responseSurvey", new ResponseSurvey());
            return "player";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/{id}", consumes = "application/json")
    public String saveResult(@PathVariable Long id, @RequestBody ResponseSurvey responseSurvey) {
        System.out.println("This is success: " + responseSurvey);
        System.out.println("Answers: " + Arrays.toString(responseSurvey.getAnswers().toArray()));
        responseSurveyRepository.save(responseSurvey);

        return "redirect:/";
    }

    @GetMapping(value = "/test/{id}", produces = "application/json")
    @ResponseBody
    public Iterable<ResponseSurvey> getResponseSurveys(@PathVariable Long id) {
        Survey survey = new Survey();
        survey.setId(id);

        return responseSurveyRepository.findAllBySurveyId(survey);
    }
}
