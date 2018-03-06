package ru.otus.slisenko.webserver.orm.cache;

public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    int getMaxElements();

    long getLifeTimeMs();

    long getIdleTimeMs();

    boolean isEternal();

    int getHitCount();

    int getMissCount();

    void dispose();
}
