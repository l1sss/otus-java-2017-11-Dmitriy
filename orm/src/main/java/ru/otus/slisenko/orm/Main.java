package ru.otus.slisenko.orm;

import ru.otus.slisenko.orm.dbservices.DBService;
import ru.otus.slisenko.orm.dbservices.DBServiceImp;
import ru.otus.slisenko.orm.datasets.UserDataSet;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = new DBServiceImp()) {
            dbService.save(new UserDataSet("Kazama", 28));
            dbService.read(1);
        }
    }
}
