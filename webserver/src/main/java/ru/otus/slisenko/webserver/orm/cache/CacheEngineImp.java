package ru.otus.slisenko.webserver.orm.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class CacheEngineImp<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;
    public static final String MAX_ELEMENTS = "max_elements";
    public static final String LIFE_TIME_MS = "life_time_ms";
    public static final String IDLE_TIME_MS = "idle_time_ms";
    public static final String IS_ETERNAL = "is_eternal";

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private int hit = 0;
    private int miss = 0;
    private final Map<K, SoftReference<CacheElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    public static class Builder<K, V> {
        private int maxElements;
        private long lifeTimeMS;
        private long idleTimeMS;
        private boolean isEternal;

        public Builder<K, V> maxElements(int quantity) {
            maxElements = quantity;
            return this;
        }

        public Builder<K, V> lifeTimeMS(long ms) {
            lifeTimeMS = ms;
            return this;
        }

        public Builder<K, V> idleTimeMS(long ms) {
            idleTimeMS = ms;
            return this;
        }

        public Builder<K, V> isEternal(boolean flag) {
            isEternal = flag;
            return this;
        }

        public CacheEngine<K, V> build() {
            return new CacheEngineImp<>(maxElements, lifeTimeMS, idleTimeMS, isEternal);
        }
    }

    private CacheEngineImp(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public void put(K key, V value) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }
        elements.put(key, new SoftReference<>(new CacheElement<>(key, value)));
        setTimerTask(key);
    }

    private void setTimerTask(K key) {
        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<K, V> element = getElement(key);
                if (element == null || isT1beforeT2(timeFunction.apply(element), System.currentTimeMillis()))
                    elements.remove(key);
                this.cancel();
            }
        };
    }

    private boolean isT1beforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public V get(K key) {
        CacheElement<K, V> element = getElement(key);
        if (element != null) {
            hit++;
            element.setAccessed();
            return element.getValue();
        } else {
            miss++;
            return null;
        }
    }

    private CacheElement<K, V> getElement(K key) {
        SoftReference<CacheElement<K, V>> softReference = elements.get(key);
        if (softReference != null)
            return softReference.get();
        return null;
    }

    @Override
    public int getMaxElements() {
        return maxElements;
    }

    @Override
    public long getLifeTimeMs() {
        return lifeTimeMs;
    }

    @Override
    public long getIdleTimeMs() {
        return idleTimeMs;
    }

    @Override
    public boolean isEternal() {
        return isEternal;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }
}
