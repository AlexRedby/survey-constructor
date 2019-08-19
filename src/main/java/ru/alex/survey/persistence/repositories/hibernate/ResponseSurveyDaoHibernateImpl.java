package ru.alex.survey.persistence.repositories.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.survey.Survey;
import ru.alex.survey.persistence.repositories.jdbc.ResponseSurveyDao;

import java.util.List;

@Repository
public class ResponseSurveyDaoHibernateImpl implements ResponseSurveyDao {
    SessionFactory sessionFactory;

    @Autowired
    public ResponseSurveyDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ResponseSurvey save(ResponseSurvey responseSurvey) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(responseSurvey);
        return responseSurvey;
    }

    @Override
    public List<ResponseSurvey> findAllBySurvey(Survey survey) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from ResponseSurvey rs where rs.survey = :survey");
        query.setParameter("survey", survey);

        return query.list();
    }
}
