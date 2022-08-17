package org.codecraftlabs.concurrency.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoizerV2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> function;

    public MemoizerV2(Computable<A, V> function) {
        this.function = function;

    }

    @Override
    public V compute(A value) throws InterruptedException {
        V result = cache.get(value);
        if (result == null) {
            result = function.compute(value);
            cache.put(value, result);
        }
        return result;
    }
}
