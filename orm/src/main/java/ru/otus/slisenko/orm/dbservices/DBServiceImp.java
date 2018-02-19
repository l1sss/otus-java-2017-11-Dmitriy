package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.dao.UsersDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBServiceImp implements DBService {
    private final Connection connection;
    private UsersDAO dao;

    public DBServiceImp() {
        connection = ConnectionHelper.getConnection();
        dao = new UsersDAO(connection);
    }

    @Override
    public void createTable() {
        dao.createTable();
    }

    @Override
    public void deleteTable() {
        dao.deleteTable();
    }

    @Override
    public List<String> getAllTables() {
        List<String> tables = new ArrayList<>();
        String[] types = {"TABLE"};
        try {
            ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", types);
            while (resultSet.next())
                tables.add(resultSet.getString("TABLE_NAME"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }

    @Override
    public void save(UserDataSet dataSet) {
        dao.save(dataSet);
    }

    @Override
    public UserDataSet load(long id) {
        return dao.load(id);
    }

    @Override
    public List<UserDataSet> loadAll() {
        return dao.loadAll();
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }
}
