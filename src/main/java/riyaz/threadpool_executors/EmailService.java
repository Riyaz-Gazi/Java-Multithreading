package riyaz.threadpool_executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService {
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    public static void sendEmail(String recipient) {
        executor.execute(() -> {
            System.out.println("Email sending to the " + recipient + " on " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Email Sent successfully to the " + recipient);
        });
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            sendEmail("user" + i + "@gmail.com");
        }
        executor.shutdown();
    }
}
