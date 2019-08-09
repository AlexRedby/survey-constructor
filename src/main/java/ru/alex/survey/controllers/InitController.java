package ru.alex.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alex.survey.persistence.models.survey.QuestionType;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.models.survey.AnswerOption;
import ru.alex.survey.persistence.models.survey.SurveyQuestion;
import ru.alex.survey.persistence.repositories.SurveyRepository;
import ru.alex.survey.persistence.repositories.jdbc.AnswerOptionDao;
import ru.alex.survey.persistence.repositories.jdbc.SurveyDao;
import ru.alex.survey.persistence.repositories.jdbc.SurveyQuestionDao;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/init")
public class InitController {

    /*private SurveyRepository surveyRepository;

    @Autowired
    public InitController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }*/

    private SurveyDao surveyDao;
    private SurveyQuestionDao surveyQuestionDao;
    private AnswerOptionDao answerOptionDao;

    @Autowired
    public InitController(SurveyDao surveyDao,
                          SurveyQuestionDao surveyQuestionDao,
                          AnswerOptionDao answerOptionDao
    ) {
        this.surveyDao = surveyDao;
        this.surveyQuestionDao = surveyQuestionDao;
        this.answerOptionDao = answerOptionDao;
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

        // JPA
        //surveyRepository.save(s);
        // JDBC
        // TODO: Вынести в отдельный метод или добавить в DAO?
        s = surveyDao.save(s);
        s.getQuestions().forEach(q -> {
            q = surveyQuestionDao.save(q);
            if(q.getType() == QuestionType.SINGLE_CHOICE || q.getType() == QuestionType.MULTIPLE_CHOICE)
                q.getAnswerOptions().forEach(o -> o = answerOptionDao.save(o));
        });

        return "redirect:/";
    }
}
