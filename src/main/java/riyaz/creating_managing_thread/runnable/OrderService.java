package riyaz.creating_managing_thread.runnable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class SMSTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("SMS sent using runnable");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EmailTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("Email send using runnable");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ETACalculationTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(5000);
        System.out.println("ETA Calculation time");
        return "25 minutes";
    }
}

public class OrderService {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SMSTask smsTask = new SMSTask();
        EmailTask emailTask = new EmailTask();
        ETACalculationTask etaCalculationTask = new ETACalculationTask();

        Thread smsThread = new Thread(smsTask);
        Thread emailThread = new Thread(emailTask);
        FutureTask<String> etaTask = new FutureTask<>(etaCalculationTask);
        Thread etaThread = new Thread(etaTask);

        System.out.println("order placed ....");
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
        System.out.println("ETA time "+eta);

        System.out.println("All task are completed");
    }
}
