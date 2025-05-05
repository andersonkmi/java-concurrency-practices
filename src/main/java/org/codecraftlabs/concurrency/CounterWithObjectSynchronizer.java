package org.codecraftlabs.concurrency;

public class CounterWithObjectSynchronizer {
    private int count;
    private final Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            count++;
        }
    }

    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}
