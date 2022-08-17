package org.codecraftlabs.concurrency.cache;

public interface Computable <A, V> {
    V compute(A value) throws InterruptedException;
}
