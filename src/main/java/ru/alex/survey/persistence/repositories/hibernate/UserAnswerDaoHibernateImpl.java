package ru.alex.survey.persistence.repositories.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.alex.survey.persistence.models.response.ResponseSurvey;
import ru.alex.survey.persistence.models.response.UserAnswer;
import ru.alex.survey.persistence.repositories.jdbc.UserAnswerDao;

import java.util.List;

@Repository
public class UserAnswerDaoHibernateImpl implements UserAnswerDao {
    SessionFactory sessionFactory;

    @Autowired
    public UserAnswerDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserAnswer save(UserAnswer userAnswer) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(userAnswer);
        return userAnswer;
    }

    @Override
    public List<UserAnswer> findAllByResponseSurvey(ResponseSurvey responseSurvey) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from UserAnswer ua where ua.responseSurvey = :responseSurvey");
        query.setParameter("responseSurvey", responseSurvey);

        return query.list();
    }
}
