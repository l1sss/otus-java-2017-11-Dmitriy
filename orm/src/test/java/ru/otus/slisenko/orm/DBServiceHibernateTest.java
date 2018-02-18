package ru.otus.slisenko.orm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.orm.datasets.AddressDataSet;
import ru.otus.slisenko.orm.datasets.PhoneDataSet;
import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.DBService;
import ru.otus.slisenko.orm.dbservices.hibernate.DBServiceHibernate;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class DBServiceHibernateTest {
    private DBService dbService;

    @Before
    public void init() {
        dbService = new DBServiceHibernate();
    }

    @After
    public void shutDown() throws Exception {
        dbService.close();
    }

    @Test
    public void addUser() {
        UserDataSet user = new UserDataSet("name", 1);

        dbService.save(user);

        assertThat(dbService.load(1).toString(), is(user.toString()));
    }

    @Test
    public void addThreeUsers() {
        UserDataSet user = new UserDataSet("name", 10);
        UserDataSet user2 = new UserDataSet("name2", 20);
        UserDataSet user3 = new UserDataSet("name3", 30);

        dbService.save(user);
        dbService.save(user2);
        dbService.save(user3);

        assertThat(dbService.load(1).toString(), is(user.toString()));
        assertThat(dbService.load(2).toString(), is(user2.toString()));
        assertThat(dbService.load(3).toString(), is(user3.toString()));
    }

    @Test
    public void addUserWithAddress() {
        UserDataSet user = new UserDataSet("name", 28);
        user.setAddress(new AddressDataSet("streetName"));

        dbService.save(user);

        assertThat(dbService.load(1).toString(), is(user.toString()));
    }

    @Test
    public void addUserWithPhone() {
        UserDataSet user = new UserDataSet("name", 22);
        user.addPhone(new PhoneDataSet("11-247"));

        dbService.save(user);

        UserDataSet loadedUser = dbService.load(1);
        assertThat(loadedUser.toString(), is(user.toString()));
        assertThat(loadedUser.getPhones(), hasSize(1));
    }

    @Test
    public void addUserWithTwoPhones() {
        UserDataSet user = new UserDataSet("name", 22);
        user.addPhone(new PhoneDataSet("11-247"));
        user.addPhone(new PhoneDataSet("14-121"));

        dbService.save(user);

        UserDataSet loadedUser = dbService.load(1);
        assertThat(loadedUser.getPhones(), hasSize(2));
    }

    @Test
    public void loadAllUsers() {
        UserDataSet user = new UserDataSet("name", 10);
        UserDataSet user2 = new UserDataSet("name2", 20);
        UserDataSet user3 = new UserDataSet("name3", 30);

        dbService.save(user);
        dbService.save(user2);
        dbService.save(user3);

        List<UserDataSet> users = dbService.loadAll();
        assertThat(users, hasSize(3));
    }
}
