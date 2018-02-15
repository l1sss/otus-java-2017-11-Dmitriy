package ru.otus.slisenko.orm;

import ru.otus.slisenko.orm.dbservices.DBService;
import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.hibernate.DBServiceHbImp;
import ru.otus.slisenko.orm.dbservices.jdbc.DBServiceImp;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = new DBServiceHbImp()) {
            //dbService.save(new UserDataSet("Kazama", 28));
            System.out.println(dbService.read(2));
        }
    }
}
