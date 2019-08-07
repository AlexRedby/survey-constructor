package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;
import ru.alex.survey.persistence.repositories.SurveyRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/init")
public class InitController {

    SurveyRepository surveyRepository;

    @Autowired
    public InitController(SurveyRepository surveyRepository) {
        super();
        this.surveyRepository = surveyRepository;
    }

    @GetMapping
    public String init() {

        List<SurveyQuestion> questions = new ArrayList<>();

        SurveyQuestion sq = new SurveyQuestion();
        sq.setText("Question 1");
        sq.setType(QuestionType.STRING_INPUT);
        questions.add(sq);

        List<AnswerOption> answers = new ArrayList<>();
        AnswerOption ao = new AnswerOption();
        ao.setText("Answer Option 1");
        answers.add(ao);
        ao = new AnswerOption();
        ao.setText("Answer Option 2");
        answers.add(ao);
        ao = new AnswerOption();
        ao.setText("Answer Option 3");
        answers.add(ao);
        ao = new AnswerOption();
        ao.setText("Answer Option 4");
        answers.add(ao);

        sq = new SurveyQuestion();
        sq.setText("Question 2");
        sq.setType(QuestionType.SINGLE_CHOICE);
        sq.setAnswerOptions(answers);
        questions.add(sq);

        answers = new ArrayList<>();
        ao = new AnswerOption();
        ao.setText("Answer Option 1");
        answers.add(ao);
        ao = new AnswerOption();
        ao.setText("Answer Option 2");
        answers.add(ao);
        ao = new AnswerOption();
        ao.setText("Answer Option 3");
        answers.add(ao);
        ao = new AnswerOption();
        ao.setText("Answer Option 4");
        answers.add(ao);

        sq = new SurveyQuestion();
        sq.setText("Question 3");
        sq.setType(QuestionType.MULTIPLE_CHOICE);
        sq.setAnswerOptions(answers);
        questions.add(sq);

        Survey s = new Survey();
        s.setName("Test Survey");
        s.setQuestions(questions);

        surveyRepository.save(s);

        return "redirect:/";
    }
}
