package riyaz.coding_example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SumOfArrayUsingTwoThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        FutureTask<Integer> sum1Future = new FutureTask<>(sum1Task);
        FutureTask<Integer> sum2Future = new FutureTask<>(sum2Task);

        Thread t1 = new Thread(sum1Future);
        Thread t2 = new Thread(sum2Future);

        t1.start();
        t2.start();

        int sum1 = sum1Future.get();
        int sum2 = sum2Future.get();

        int sum = sum1 + sum2;

        System.out.println("Sum " + sum);
    }
}
