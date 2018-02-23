package ru.otus.slisenko.orm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.DBService;
import ru.otus.slisenko.orm.dbservices.DBServiceImp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class DBServiceTest {
    private DBService dbService;

    @Before
    public void init() {
        dbService = new DBServiceImp();
        dbService.createTable();
    }

    @After
    public void shutDown() throws Exception {
        dbService.deleteTable();
        dbService.close();
    }

    @Test
    public void createTable() {
        assertThat(dbService.getAllTables(), hasItem("users"));
    }

    @Test
    public void deleteTable() {
        assertThat(dbService.getAllTables(), hasItem("users"));

        dbService.deleteTable();

        assertThat(dbService.getAllTables(), not(hasItem("users")));
    }

    @Test
    public void addUser() {
        UserDataSet user = new UserDataSet("name", 21);
        dbService.save(user);

        assertThat(dbService.load(1).toString(), is(user.toString()));
    }

    @Test
    public void loadAllUsers() {
        UserDataSet user = new UserDataSet("name", 28);
        UserDataSet user2 = new UserDataSet("name2", 14);
        UserDataSet user3 = new UserDataSet("name3", 22);
        dbService.save(user);
        dbService.save(user2);
        dbService.save(user3);

        assertThat(dbService.loadAll(), hasSize(3));
    }
}
