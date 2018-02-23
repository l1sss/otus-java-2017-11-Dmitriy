package ru.otus.slisenko.orm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.otus.slisenko.orm.cache.CacheEngine;
import ru.otus.slisenko.orm.cache.CacheEngineImp;
import ru.otus.slisenko.orm.datasets.UserDataSet;
import ru.otus.slisenko.orm.dbservices.DBService;
import ru.otus.slisenko.orm.dbservices.DBServiceImp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CacheEngineTest {
    private DBService dbService;
    CacheEngine<Long, UserDataSet> cacheEngine;

    @Before
    public void init() {
        cacheEngine = new CacheEngineImp.Builder<Long, UserDataSet>()
                .maxElements(5)
                .lifeTimeMS(500)
                .idleTimeMS(0)
                .isEternal(false)
                .build();
        dbService = new DBServiceImp(cacheEngine);
        dbService.createTable();
    }

    @After
    public void shutDown() throws Exception {
        dbService.deleteTable();
        dbService.close();
    }

    @Test
    public void shouldIncrementMiss() {
        UserDataSet user = new UserDataSet("name", 31);
        dbService.save(user);

        dbService.load(1);

        assertThat(cacheEngine.getMissCount(), is(1));
    }

    @Test
    public void shouldSecondIncrementMissAfterLifeTime() throws Exception {
        UserDataSet user = new UserDataSet("name", 28);
        dbService.save(user);

        dbService.load(1);
        Thread.sleep(500);
        dbService.load(1);

        assertThat(cacheEngine.getMissCount(), is(2));
    }

    @Test
    public void shouldIncrementHit() {
        UserDataSet user = new UserDataSet("name", 28);
        dbService.save(user);

        dbService.load(1);
        dbService.load(1);

        assertThat(cacheEngine.getHitCount(), is(1));
    }
}
