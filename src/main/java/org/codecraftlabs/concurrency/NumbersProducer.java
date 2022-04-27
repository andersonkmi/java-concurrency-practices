package org.codecraftlabs.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class NumbersProducer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int poisonPill;
    private final int poisonPillPerProducer;

    public NumbersProducer(BlockingQueue<Integer> queue, int poisonPill, int poisonPillPerProducer) {
        this.queue = queue;
        this.poisonPill = poisonPill;
        this.poisonPillPerProducer = poisonPillPerProducer;
    }

    @Override
    public void run() {
        try {
            generateNumbers();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private void generateNumbers() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            queue.put(ThreadLocalRandom.current().nextInt(100));
        }
        for (int j = 0; j < poisonPillPerProducer; j++) {
            queue.put(poisonPill);
        }
    }
}
