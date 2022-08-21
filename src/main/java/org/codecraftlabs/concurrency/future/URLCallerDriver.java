package org.codecraftlabs.concurrency.future;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class URLCallerDriver {
    public void execute() {
        var url1 = "https://www.uol.com.br";
        var url2 = "https://www.dutchnews.nl";
        var url3 = "https://g1.globo.com/";

        APICaller caller1 = new APICaller(url1);
        APICaller caller2 = new APICaller(url2);
        APICaller caller3 = new APICaller(url3);

        FutureTask<String> futureTask1 = new FutureTask<>(caller1);
        FutureTask<String> futureTask2 = new FutureTask<>(caller2);
        FutureTask<String> futureTask3 = new FutureTask<>(caller3);

        // Starting first task
        System.out.println("Starting first task");
        futureTask1.run();

        // Counting to simulate some operation
        simulateHeavyOperations(1500000);

        // Starts the second task
        System.out.println("Starting second task");
        futureTask2.run();

        simulateHeavyOperations(10000000);

        // Starts the 3rd task
        System.out.println("Starting third task");
        futureTask3.run();

        try {
            System.out.println(futureTask1.get());
            System.out.println("================================================================");
            System.out.println(futureTask2.get());
            System.out.println("================================================================");
            System.out.println(futureTask3.get());
        } catch (CancellationException | InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }
    }

    private void simulateHeavyOperations(long total) {
        System.out.println("Simulating heavy operation");
        for(long counter = 0; counter < total; counter++) {
            // no action performed
        }
    }

    public static void main(String[] args) {
        URLCallerDriver driver = new URLCallerDriver();
        driver.execute();
    }
}
