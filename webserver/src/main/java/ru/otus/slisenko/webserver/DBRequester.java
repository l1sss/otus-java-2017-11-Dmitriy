package ru.otus.slisenko.webserver;

import ru.otus.slisenko.webserver.orm.datasets.UserDataSet;
import ru.otus.slisenko.webserver.orm.dbservices.DBService;

import java.util.ArrayList;
import java.util.List;

class DBRequester implements Runnable {
    private DBService dbService;

    DBRequester(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void run() {
        List<Long> listId = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            UserDataSet user = new UserDataSet("name" + i, 28);
            dbService.save(user);
            listId.add(user.getId());
        }

        while (true) {
            int indexTmp = (int) (Math.random() * 12);
            dbService.load(listId.get(indexTmp));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
