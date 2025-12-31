package riyaz.producer_consumer.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Chef implements Runnable {
    BlockingQueue<String> queue;

    public Chef(BlockingQueue<String> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            int i = 1;
            while (true) {
                queue.put("Dish " + i);
                System.out.println("Chef cooked dish " + i);
                i++;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Waiter implements Runnable {
    BlockingQueue<String> queue;

    public Waiter(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String dish = queue.take();
                System.out.println("Waiter served dish " + dish);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerMain {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        Thread t1 = new Thread(new Chef(queue));
        Thread t2 = new Thread(new Waiter(queue));

        t1.start();
        t2.start();
    }
}
