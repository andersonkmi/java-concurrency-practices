package org.codecraftlabs.concurrency.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MemoizerV3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> function;

    public MemoizerV3(Computable<A, V> function) {
        this.function = function;

    }

    @Override
    public V compute(A value) throws InterruptedException {
        Future<V> f = cache.get(value);
        if (f == null) {
            Callable<V> eval = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return function.compute(value);
                }
            };
            FutureTask<V> ft = new FutureTask<>(eval);
            f = ft;
            cache.put(value, ft);
            ft.run();
        }

        try {
            return f.get();
        } catch (ExecutionException exception) {
            throw new InterruptedException("Failed");
        }
    }
}
