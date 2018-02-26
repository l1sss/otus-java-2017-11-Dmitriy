package ru.otus.slisenko.orm.dbservices;

import ru.otus.slisenko.orm.cache.CacheEngine;
import ru.otus.slisenko.orm.cache.CacheEngineImp;
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
    private CacheEngine<Long, UserDataSet> cacheEngine;

    public DBServiceImp() {
        connection = ConnectionHelper.getConnection();
        dao = new UsersDAO(connection);
        cacheEngine = new CacheEngineImp.Builder<Long, UserDataSet>()
                .maxElements(5)
                .lifeTimeMS(1000)
                .idleTimeMS(0)
                .isEternal(false)
                .build();
    }

    public DBServiceImp(CacheEngine<Long, UserDataSet> cacheEngine) {
        connection = ConnectionHelper.getConnection();
        dao = new UsersDAO(connection);
        this.cacheEngine = cacheEngine;
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
        cacheEngine.put(dataSet.getId(), dataSet);
    }

    @Override
    public UserDataSet load(long id) {
        UserDataSet dataSet = cacheEngine.get(id);
        if (dataSet == null) {
            dataSet = dao.load(id);
            if (dataSet != null)
                cacheEngine.put(dataSet.getId(), dataSet);
        }
        return dao.load(id);
    }

    @Override
    public List<UserDataSet> loadAll() {
        List<UserDataSet> result = dao.loadAll();
        for (UserDataSet user : result)
            cacheEngine.put(user.getId(), user);
        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
        cacheEngine.dispose();
    }
}
