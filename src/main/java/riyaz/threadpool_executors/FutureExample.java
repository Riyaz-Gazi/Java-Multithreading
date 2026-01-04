package riyaz.threadpool_executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);


        Future<Integer> future = executor.submit(() -> {
            Thread.sleep(2000);
            return 77;
        });

        System.out.println("Doing other works");
        Integer result = future.get(); // blocks until the results in ready
        System.out.println("Final Result " + result);
        executor.shutdown();
    }
}
