package ru.otus.slisenko.orm;

import ru.otus.slisenko.orm.DBService.DBService;
import ru.otus.slisenko.orm.DBService.DBServiceImp;
import ru.otus.slisenko.orm.dataset.UserDataSet;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = new DBServiceImp()) {
            System.out.println(dbService.getMetaData());
            dbService.prepareTable();
            dbService.addUser(new UserDataSet("Leonardo", 34));
            dbService.addUser(new UserDataSet("Michelangelo", 34));
            dbService.addUser(new UserDataSet("Donatello", 34));
            dbService.addUser(new UserDataSet("Raphael", 34));
            System.out.println(dbService.getUser(3, UserDataSet.class));
            dbService.deleteTable();
        }
    }
}
