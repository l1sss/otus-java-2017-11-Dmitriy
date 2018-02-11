package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.dao.UserDataSetDAO;

import java.sql.Connection;

public class DBServiceImp implements DBService {
    private final Connection connection;

    public DBServiceImp() {
        connection = ConnectionHelper.getConnection();
    }

    @Override
    public void save(UserDataSet dataSet) {
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        dao.save(dataSet);
        System.out.println("ADDED: " + dataSet.toString());
    }

    @Override
    public UserDataSet read(long id) {
        UserDataSetDAO dao = new UserDataSetDAO(connection);
        UserDataSet dataSet = dao.read(id);
        System.out.println(
                dataSet != null ? "LOADED: " + dataSet.toString() : "IS EMPTY");
        return dataSet;
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }
}
