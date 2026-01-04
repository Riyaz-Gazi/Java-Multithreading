package riyaz.coding_example;

import java.util.concurrent.*;

public class SumOfArrayUsingExecutors {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        int[] nums = {1, 2, 3, 4, 5, 6};
        int n = nums.length;
        int mid = n / 2;

        Callable<Integer> sum1Task = () -> {
            int sum = 0;
            for (int i = 0; i < mid; i++) {
                sum += nums[i];
            }
            return sum;
        };

        Callable<Integer> sum2Task = () -> {
            int sum = 0;
            for (int i = mid; i < n; i++) {
                sum += nums[i];
            }
            return sum;
        };

        Future<Integer> f1 = executor.submit(sum1Task);
        Future<Integer> f2 = executor.submit(sum2Task);

        int sum = f1.get() + f2.get();
        System.out.println("Total Sum " + sum);
        executor.shutdown();
    }
}
