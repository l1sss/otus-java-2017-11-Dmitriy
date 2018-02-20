package ru.otus.slisenko.orm.dbservices.hibernate;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.DBService;
import ru.otus.slisenko.orm.dbservices.hibernate.dao.UsersHibernateDAO;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernate implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceHibernate() {
        Configuration configuration = ConfigurationHelper.getDefaultConfig();
        sessionFactory = createSessionFactory(configuration);
    }

    public DBServiceHibernate(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void save(UserDataSet dataSet) {
        runInSession(session -> {
            UsersHibernateDAO dao = new UsersHibernateDAO(session);
            dao.save(dataSet);
            return null;
        });
    }

    @Override
    public UserDataSet load(long id) {
        return runInSession(session -> {
            UsersHibernateDAO dao = new UsersHibernateDAO(session);
            return dao.load(id);
        });
    }

    @Override
    public UserDataSet loadByName(String name) {
        return runInSession(session -> {
            UsersHibernateDAO dao = new UsersHibernateDAO(session);
            return dao.loadByName(name);
        });
    }

    @Override
    public List<UserDataSet> loadAll() {
        return runInSession(session -> {
            UsersHibernateDAO dao = new UsersHibernateDAO(session);
            return dao.loadAll();
        });
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }
}
