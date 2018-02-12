package ru.otus.slisenko.orm;

import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.DBService;
import ru.otus.slisenko.orm.dbservices.DBServiceImp;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        try (DBService dbService = new DBServiceImp()) {
            dbService.save(new UserDataSet("Willis", 62));
            dbService.read(3);
        }
    }
}
