package riyaz.creating_managing_thread.thread;

class SMSThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println("SMS send using Thread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EmailThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("Email send using Thread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ETACalculatorThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println("Estimated times 25 minutes");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class OrderService {
    public static void main(String[] args) {
        SMSThread smsThread = new SMSThread();
        EmailThread emailThread = new EmailThread();
        ETACalculatorThread etaCalculatorThread = new ETACalculatorThread();

        System.out.println("Order placed ....");
        smsThread.start();
        System.out.println("Task 1 ongoing");
        emailThread.start();
        System.out.println("Task 2 ongoing");
        etaCalculatorThread.start();
        System.out.println("Task 3 ongoing");

        try {
            smsThread.join();
            emailThread.join();
            etaCalculatorThread.join();
            System.out.println("All task are complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
