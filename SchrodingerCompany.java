import java.util.concurrent.Semaphore;

public class SchrodingerCompany {
    static final int BUFFER_SIZE = 100;
    static int[] particleBuffer = new int[BUFFER_SIZE];
    static int nextIn = 0; // Index for the next particle to be placed in the buffer
    static int nextOut = 0; // Index for the next particle to be fetched from the buffer

    // Semaphores
    static Semaphore empty = new Semaphore(BUFFER_SIZE); // Tracks empty slots
    static Semaphore full = new Semaphore(0); // Tracks filled slots
    static Semaphore mutex = new Semaphore(1); // Ensures mutual exclusion

    public static void main(String[] args) {
        // Create multiple producer threads and one consumer thread
        Thread[] producers = new Thread[5];
        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Thread(new Producer());
            producers[i].start();
        }

        Thread consumer = new Thread(new Consumer());
        consumer.start();

        // Join threads to the main thread to ensure they complete
        try {
            for (Thread producer : producers) {
                producer.join();
            }
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // Produce a pair of particles
                    int p1 = (int) (Math.random() * 100); // Random particle 1
                    int p2 = (int) (Math.random() * 100); // Random particle 2

                    // Wait for two empty slots
                    empty.acquire(2);

                    // Ensure mutual exclusion when accessing the buffer
                    mutex.acquire();
                    particleBuffer[nextIn] = p1;
                    particleBuffer[(nextIn + 1) % BUFFER_SIZE] = p2;
                    nextIn = (nextIn + 2) % BUFFER_SIZE; // Move to the next pair of slots
                    mutex.release();

                    // Signal that two slots have been filled
                    full.release(2);

                    System.out.println("Produced pair: " + p1 + ", " + p2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // Wait for two filled slots
                    full.acquire(2);

                    // Ensure mutual exclusion when accessing the buffer
                    mutex.acquire();
                    int p1 = particleBuffer[nextOut];
                    int p2 = particleBuffer[(nextOut + 1) % BUFFER_SIZE];
                    nextOut = (nextOut + 2) % BUFFER_SIZE; // Move to the next pair of slots
                    mutex.release();

                    // Signal that two slots have been freed
                    empty.release(2);

                    // Package and ship the pair
                    System.out.println("Consumed pair: " + p1 + ", " + p2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
