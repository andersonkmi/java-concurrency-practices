package org.codecraftlabs.concurrency;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.printf("Thread running: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}
