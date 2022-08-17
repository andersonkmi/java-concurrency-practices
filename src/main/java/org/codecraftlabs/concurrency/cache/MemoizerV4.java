package org.codecraftlabs.concurrency.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MemoizerV4<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> function;

    public MemoizerV4(Computable<A, V> function) {
        this.function = function;

    }

    @Override
    public V compute(A value) throws InterruptedException {
        while(true) {
            Future<V> f = cache.get(value);
            if (f == null) {
                Callable<V> eval = () -> function.compute(value);
                FutureTask<V> ft = new FutureTask<>(eval);
                f = cache.putIfAbsent(value, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }

            try {
                return f.get();
            } catch (CancellationException exception) {
                cache.remove(value, f);
            } catch (ExecutionException exception) {
                throw new InterruptedException("Failed");
            }
        }
    }
}
