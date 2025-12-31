package riyaz.creating_managing_thread.lambda;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class OrderService {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Runnable smsTask = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("SMS send using lambda");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable emailTask = () -> {
            try {
                Thread.sleep(3000);
                System.out.println("email send using lambda");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Callable<String> etaCalculationTask = () -> {
            Thread.sleep(5000);
            System.out.println("eta calculation using lambda");
            return "25 minutes";
        };

        Thread smsThread = new Thread(smsTask);
        Thread emailThread = new Thread(emailTask);
        FutureTask<String> etaTask = new FutureTask<>(etaCalculationTask);
        Thread etaThread = new Thread(etaTask);

        System.out.println("Order placed ....");
        smsThread.start();
        System.out.println("Task 1 ongoing");
        emailThread.start();
        System.out.println("Task 2 ongoing");
        etaThread.start();
        System.out.println("Task 3 ongoing");

        smsThread.join();
        emailThread.join();
        etaThread.join();

        String eta = etaTask.get();
        System.out.println("ETA Time "+eta);
        System.out.println("All task are completed");

    }
}
