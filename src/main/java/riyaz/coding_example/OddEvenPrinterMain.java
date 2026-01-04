package riyaz.coding_example;

class PrintOddEven {
    private int num = 1;
    private final int n;

    PrintOddEven(int n) {
        this.n = n;
    }

    public synchronized void printOdd() throws InterruptedException {
        while (true) {
            while (num % 2 == 0 && num <= n) {
                wait();
            }
            if (num > n) {
                notifyAll();
                break;
            }
            System.out.println("Odd " + num);
            num++;
            notifyAll();
        }
    }

    public synchronized void printEven() throws InterruptedException {
        while (true) {
            while (num % 2 != 0 && num <= n) {
                wait();
            }
            if (num > n) {
                notifyAll();
                break;
            }
            System.out.println("Even " + num);
            num++;
            notifyAll();
        }
    }
}

public class OddEvenPrinterMain {
    public static void main(String[] args) {
        PrintOddEven printOddEven = new PrintOddEven(10);

        Thread odd = new Thread(() -> {
            try {
                printOddEven.printOdd();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread even = new Thread(() -> {
            try {
                printOddEven.printEven();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        odd.start();
        even.start();

    }
}
