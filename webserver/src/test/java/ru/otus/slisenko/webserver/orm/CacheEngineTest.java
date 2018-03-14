package ru.otus.slisenko.webserver.orm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.webserver.orm.cache.CacheEngine;
import ru.otus.slisenko.webserver.orm.cache.CacheEngineImp;
import ru.otus.slisenko.webserver.orm.datasets.UserDataSet;
import ru.otus.slisenko.webserver.orm.dbservices.DBService;
import ru.otus.slisenko.webserver.orm.dbservices.DBServiceImp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CacheEngineTest {
    private CacheEngine<Long, UserDataSet> cacheEngine;
    private DBService dbService;

    @Before
    public void init() {
        cacheEngine = new CacheEngineImp<>();
        dbService = new DBServiceImp(cacheEngine);
        dbService.createTable();
    }

    @After
    public void shutDown() throws Exception {
        dbService.deleteTable();
        dbService.close();
    }

    @Test
    public void shouldIncrementHit() {
        UserDataSet user = new UserDataSet("name", 28);
        dbService.save(user);

        dbService.load(1);

        assertThat(cacheEngine.getMissCount(), is(0));
        assertThat(cacheEngine.getHitCount(), is(1));
    }

    @Test
    public void shouldIncrementMissAfterLifeTime() throws Exception {
        UserDataSet user = new UserDataSet("name", 28);
        dbService.save(user);
        long overLifeTime = cacheEngine.getLifeTimeMs() + 100;

        Thread.sleep(overLifeTime);
        dbService.load(1);

        assertThat(cacheEngine.getMissCount(), is(1));
        assertThat(cacheEngine.getHitCount(), is(0));
    }
}
