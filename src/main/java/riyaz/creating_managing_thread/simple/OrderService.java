package riyaz.creating_managing_thread.simple;

public class OrderService {

    public static void sendSms() {
        try {
            Thread.sleep(2000);
            System.out.println("SMS Sent !!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail() {
        try {
            Thread.sleep(3000);
            System.out.println("Email Sent !!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String calculateETA() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "25 minutes";
    }

    public static void main(String[] args) {
        System.out.println("Placing order ...");
        sendSms();
        System.out.println("Task 1 done");
        sendEmail();
        System.out.println("Task 2 done");
        calculateETA();
        System.out.println("Task 3 done");
        System.out.println("All Task are complete");

    }
}
