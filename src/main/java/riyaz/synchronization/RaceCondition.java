package riyaz.synchronization;

class PurchaseCounter {
    // Shared count value
    private int count = 0;

    // Increment the count value
    public void increment() {
        // READ current value
        // INCREMENT it
        // WRITE it back
        count++;   // No Atomic , unsafe
    }

    public int getCount() {
        return count;
    }
}

class PurchaseCounterSyncMethod {
    private int count = 0;

    // Entire method is protected by the instance’s monitor lock
    public synchronized void increment() {
        count++;          // atomic under the lock
    }

    public int getCount() {
        return count;
    }
}

class PurchaseCounterSyncBlock {
    private int count = 0;

    public void increment() {
        // Lock only the code that truly needs protection
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        // No lock needed for simple read, or use block if strict consistency required
        return count;
    }
}

class PurchaseCounterSyncLock {
    private final Object lock = new Object();
    private int count = 0;

    public void increment() {
        // Lock only the code that truly needs protection
        synchronized (lock) {
            count++;
        }
    }

    public int getCount() {
        // No lock needed for simple read, or use block if strict consistency required
        return count;
    }
}

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        // create a shared counter
//        PurchaseCounter counter = new PurchaseCounter();
        PurchaseCounterSyncMethod counter = new PurchaseCounterSyncMethod();

        // task that bumps the counter 10000 times
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        };

        // Run the same tasks in two thread
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Final Count : " + counter.getCount());
    }
}
