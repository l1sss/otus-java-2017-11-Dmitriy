package ru.otus.slisenko.orm.dbservices.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.slisenko.orm.datasets.UserDataSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UsersHibernateDAO {
    private Session session;

    public UsersHibernateDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet load(long id) {
        return session.load(UserDataSet.class, id);
    }

    public UserDataSet loadByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        Root<UserDataSet> root = criteria.from(UserDataSet.class);
        criteria.where(builder.equal(root.get("name"), name));
        Query<UserDataSet> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<UserDataSet> loadAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = builder.createQuery(UserDataSet.class);
        criteria.from(UserDataSet.class);
        return session.createQuery(criteria).list();
    }
}
