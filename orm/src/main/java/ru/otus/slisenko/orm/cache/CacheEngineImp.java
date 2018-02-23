package ru.otus.slisenko.orm.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImp<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;
    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;
    private final Map<K, SoftReference<CacheElement<K, V>>> elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();
    private int hit = 0;
    private int miss = 0;

    public static class Builder<K, V> {
        private int maxElements = 8;
        private int lifeTimeMS = 0;
        private int idleTimeMS = 0;
        private boolean isEternal = true;

        public Builder<K, V> maxElements(int quantity) {
            maxElements = quantity;
            return this;
        }

        public Builder<K, V> lifeTimeMS(int ms) {
            lifeTimeMS = ms;
            return this;
        }

        public Builder<K, V> idleTimeMS(int ms) {
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
    public void put(CacheElement<K, V> element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }
        K key = element.getKey();
        elements.put(key, new SoftReference<>(element));
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
    public CacheElement<K, V> get(K key) {
        CacheElement<K, V> element = getElement(key);
        if (element != null) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    private CacheElement<K, V> getElement(K key) {
        if (elements.get(key) != null)
            return elements.get(key).get();
        return null;
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
