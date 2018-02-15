package ru.otus.slisenko.orm.dbservices.hibernate.dao;

import org.hibernate.Session;
import ru.otus.slisenko.orm.datasets.UserDataSet;

public class UsersHbDAO {
    private Session session;

    public UsersHbDAO(Session session) {
        this.session = session;
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }
}
