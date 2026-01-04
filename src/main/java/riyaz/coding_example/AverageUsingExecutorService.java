package riyaz.coding_example;

import java.util.concurrent.*;

public class AverageUsingExecutorService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = {10, 20, 30, 40, 50};

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int a : arr) {
                sum += a;
            }
            return sum;
        };

        Callable<Integer> countTask = () -> arr.length;

        Future<Integer> sumFuture = executor.submit(sumTask);
        Future<Integer> countFuture = executor.submit(countTask);

        int sum = sumFuture.get(); // blocking
        int count = countFuture.get(); // blocking

        System.out.println("Sum " + sum);
        System.out.println("Count " + count);

        double average = (double) sum / count;
        System.out.println("Average " + average);
        executor.shutdown();
    }
}
