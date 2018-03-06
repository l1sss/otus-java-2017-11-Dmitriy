package ru.otus.slisenko.webserver.orm.dbservices;

import ru.otus.slisenko.webserver.orm.cache.CacheEngine;
import ru.otus.slisenko.webserver.orm.cache.CacheEngineImp;
import ru.otus.slisenko.webserver.orm.datasets.UserDataSet;
import ru.otus.slisenko.webserver.orm.dbservices.dao.UsersDAO;
import ru.otus.slisenko.webserver.util.PropertiesHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBServiceImp implements DBService {
    private static final String CACHE_PROPERTIES_NAME = "cfg/cache.properties";
    private final Connection connection;
    private UsersDAO dao;
    private CacheEngine<Long, UserDataSet> cacheEngine;

    public DBServiceImp() {
        connection = ConnectionHelper.getConnection();
        dao = new UsersDAO(connection);

        Properties cacheProperties = PropertiesHelper.getProperties(CACHE_PROPERTIES_NAME);
        int maxElements = Integer.valueOf(cacheProperties.getProperty(CacheEngineImp.MAX_ELEMENTS));
        long lifeTimeMS = Long.valueOf(cacheProperties.getProperty(CacheEngineImp.LIFE_TIME_MS));
        long idleTimeMS = Long.valueOf(cacheProperties.getProperty(CacheEngineImp.IDLE_TIME_MS));
        boolean isEternal = Boolean.valueOf(cacheProperties.getProperty(CacheEngineImp.IS_ETERNAL));
        cacheEngine = new CacheEngineImp.Builder<Long, UserDataSet>()
                .maxElements(maxElements)
                .lifeTimeMS(lifeTimeMS)
                .idleTimeMS(idleTimeMS)
                .isEternal(isEternal)
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
    public CacheEngine<Long, UserDataSet> getCache() {
        return cacheEngine;
    }

    @Override
    public void close() throws Exception {
        connection.close();
        cacheEngine.dispose();
    }
}
