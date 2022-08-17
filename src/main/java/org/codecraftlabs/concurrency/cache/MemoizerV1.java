package org.codecraftlabs.concurrency.cache;

import javax.annotation.concurrent.GuardedBy;
import java.util.HashMap;
import java.util.Map;

public class MemoizerV1<A, V> implements Computable<A, V> {
    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> function;

    public MemoizerV1(Computable<A, V> function) {
        this.function = function;

    }

    @Override
    public synchronized V compute(A value) throws InterruptedException {
        V result = cache.get(value);
        if (result == null) {
            result = function.compute(value);
            cache.put(value, result);
        }
        return result;
    }
}
