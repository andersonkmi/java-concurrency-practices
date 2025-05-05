package org.codecraftlabs.concurrency;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        Thread thread0 = new Thread(new MyRunnable());
        Thread thread1 = new Thread(new MyRunnable());
        thread0.start();
        thread1.start();
    }
}
