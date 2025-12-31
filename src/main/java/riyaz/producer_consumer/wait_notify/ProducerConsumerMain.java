package riyaz.producer_consumer.wait_notify;

/**
 * Producer–Consumer example using a restaurant analogy.
 *
 * <p><b>Roles</b></p>
 * <ul>
 *   <li><b>Chef</b> – Producer: cooks dishes</li>
 *   <li><b>Waiter</b> – Consumer: serves dishes</li>
 *   <li><b>OrderCounter</b> – Shared buffer with limited capacity</li>
 * </ul>
 *
 * <p><b>Rules</b></p>
 * <ul>
 *   <li>If the counter is full, the chef waits</li>
 *   <li>If the counter is empty, the waiter waits</li>
 *   <li>All operations must be thread-safe</li>
 * </ul>
 */


class OrderCounter {
    private int orders = 0;
    private final int MAX_ORDER = 3;


    public synchronized void cookOrder() throws InterruptedException {
        while (orders == MAX_ORDER) {
            wait();
        }

        orders++;

        System.out.println("Chef cooked an order . Order on counter " + orders);
        notifyAll();
    }

    public synchronized void serveOrder() throws InterruptedException {
        while (orders == 0) {
            wait();
        }

        orders--;
        System.out.println("Waiter served an order . Order on counter " + orders);
        notifyAll();
    }

}


class Chef implements Runnable {
    private OrderCounter counter;

    public Chef(OrderCounter counter) {
        this.counter = counter;
    }


    @Override
    public void run() {

        while (true) {
            try {
                counter.cookOrder();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Waiter implements Runnable {

    private OrderCounter counter;

    public Waiter(OrderCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                counter.serveOrder();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}


public class ProducerConsumerMain {
    public static void main(String[] args) {
        OrderCounter counter = new OrderCounter();

        Thread t1 = new Thread(new Chef(counter));
        Thread t2 = new Thread(new Waiter(counter));

        t1.start();
        t2.start();
    }
}
