package tuf.deadlock;

import java.util.Arrays;

class BankAccount {

    // Name helps us identify the account in logs
    private final String name;

    // Shared mutable state that needs protection
    private int balance;

    // Constructor – sets initial state
    public BankAccount(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    // Read-only helper used only for logging
    public String getName() {
        return name;
    }

    // Deposit is a critical section – guard with the object’s lock
    public synchronized void deposit(int amount) {
        balance += amount;
    }

    // Withdraw is also a critical section – same lock
    public synchronized void withdraw(int amount) {
        balance -= amount;
    }

    // Another read-only helper for debugging
    public int getBalance() {
        return balance;
    }
}

// ──────────────────────────────────────────────────────────────
// Runnable that transfers money from one account to another
// ──────────────────────────────────────────────────────────────
class TransferTask implements Runnable {

    private final BankAccount from;
    private final BankAccount to;
    private final int amount;

    // Capture the two accounts and transfer amount
    public TransferTask(BankAccount from, BankAccount to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {

        // First lock: ‘from’ account – thread now owns its monitor
        synchronized (from) {
            System.out.println(Thread.currentThread().getName() +
                    " locked " + from.getName());

            // Artificial delay widens timing window
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }

            // Second lock: ‘to’ account – may block if another thread owns it
            synchronized (to) {
                System.out.println(Thread.currentThread().getName() +
                        " locked " + to.getName());

                // Critical update – atomic with respect to both locks
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println("Transferred " + amount + " from " +
                        from.getName() + " to " + to.getName());
            }
        }
    }
}


// ─────────────────────────────────────────────
class LockOrderingSimple {

    // Represents a shared resource (could be account, file, etc.)
    static class Resource {
        int id;
        int value;

        public Resource(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    public static void main(String[] args) {

        // Create two resources with different IDs
        Resource r1 = new Resource(101, 500);
        Resource r2 = new Resource(102, 300);

        // Task 1 tries to transfer from r1 → r2
        Runnable task1 = () -> transfer(r1, r2, 50);

        // Task 2 tries to transfer from r2 → r1 (reverse order)
        Runnable task2 = () -> transfer(r2, r1, 30);

        // Start both threads
        new Thread(task1, "T1").start();
        new Thread(task2, "T2").start();
    }

    // Method that performs the transfer with lock ordering
    public static void transfer(Resource a, Resource b, int amount) {

        // Create an array of the two resources involved
        Resource[] locks = new Resource[]{a, b};

        // Sort the locks based on unique ID (global lock ordering)
        Arrays.sort(locks, (x, y) -> Integer.compare(x.id, y.id));

        // Always acquire locks in sorted (consistent) order
        synchronized (locks[0]) {
            System.out.println(Thread.currentThread().getName() + " locked " + locks[0].id);

            try {
                Thread.sleep(50); // Add delay to increase interleaving
            } catch (InterruptedException ignored) {
            }

            synchronized (locks[1]) {
                System.out.println(Thread.currentThread().getName() + " locked " + locks[1].id);
                System.out.println("Transferred " + amount + " from " + a.id + " to " + b.id);
            }
        }
    }
}


public class DeadlockSimpleExample {
    public static void main(String[] args) throws InterruptedException {
        // Create two independent accounts
        BankAccount accountA = new BankAccount("Account-A", 1000);
        BankAccount accountB = new BankAccount("Account-B", 1000);

        // Thread T1 transfers A → B
        Thread t1 = new Thread(new TransferTask(accountA, accountB, 100), "T1");

        // Thread T2 transfers B → A (reverse order)
        Thread t2 = new Thread(new TransferTask(accountB, accountA, 200), "T2");

        // Kick off both threads
        t1.start();
        t2.start();

        // Waiting for both the threads to complete executions
        t1.join();
        t2.join();

        /* This line never gets executed because
        both the threads were stuck in deadlock */
        System.out.println("Both threads finished execution.");

    }
}
