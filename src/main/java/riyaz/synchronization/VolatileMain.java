package riyaz.synchronization;

class SharedData {
    volatile boolean flag = false;

    public void writer() {
        flag = true;
    }

    public void reader() {
        if (flag) {
            // guaranteed to see true if another thread wrote it
        }
    }
}

class Counter {
    volatile int count = 0;

    public void increment() {
        count++; // Still unsafe!
    }
}

public class VolatileMain {
    public static void main(String[] args) {

    }
}
