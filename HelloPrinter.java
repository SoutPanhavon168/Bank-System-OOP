import java.util.concurrent.Semaphore;

public class HelloPrinter {
    // Initialize semaphores
    static Semaphore a = new Semaphore(1);  // Allows Process 1 to start
    static Semaphore b = new Semaphore(0);  // Blocks Process 2 initially
    static Semaphore c = new Semaphore(0);  // Blocks Process 3 initially
    
    public static void main(String[] args) {
        // Create and start the three processes
        Thread process1 = new Thread(new Process1());
        Thread process2 = new Thread(new Process2());
        Thread process3 = new Thread(new Process3());

        process1.start();
        process2.start();
        process3.start();

        // Join threads to the main thread to ensure they complete
        try {
            process1.join();
            process2.join();
            process3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();  // For a new line after the output
    }

    static class Process1 implements Runnable {
        @Override
        public void run() {
            try {
                a.acquire();  // wait(a)
                System.out.print("H");
                System.out.print("E");
                b.release();  // signal(b)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Process2 implements Runnable {
        @Override
        public void run() {
            try {
                b.acquire();  // wait(b)
                System.out.print("L");
                // Removed signal for semaphore d
                c.release();  // signal(c) for "O"
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Process3 implements Runnable {
        @Override
        public void run() {
            try {
                c.acquire();  // wait(c) for "L" and "O"
                System.out.print("L");
                System.out.print("O");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
