package riyaz.coding_example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class AverageUsingCallable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = {10, 20, 30, 40, 50};

        Callable<Integer> sumTask = () -> {
            int sum = 0;
            for (int a : arr) {
                sum += a;
            }
            return sum;
        };

        Callable<Integer> countTask = () -> {
            return arr.length;
        };

        FutureTask<Integer> sumFuture = new FutureTask<>(sumTask);
        FutureTask<Integer> countFuture = new FutureTask<>(countTask);

        Thread t1 = new Thread(sumFuture);
        Thread t2 = new Thread(countFuture);

        t1.start();
        t2.start();

        Integer sum = sumFuture.get();
        Integer count = countFuture.get();

        System.out.println("Sum " + sum);
        System.out.println("Count " + count);

        double average = (double) sum / count;
        System.out.println("Average " +average);
    }
}
