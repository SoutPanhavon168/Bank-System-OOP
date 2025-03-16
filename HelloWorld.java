import java.util.concurrent.Semaphore;

public class HelloWorld {
    
    // Semaphores to control the execution order of processes
    static Semaphore a = new Semaphore(1); // Process 1 starts first
    static Semaphore b = new Semaphore(0); // Process 2 waits for Process 1
    static Semaphore c = new Semaphore(0); // Process 3 waits for Process 2
    
    // Flag to ensure HELLO is printed only once
    static boolean printed = false;

    public static void main(String[] args) {
        // Creating three threads to simulate Process 1, Process 2, and Process 3
        Thread process1 = new Thread(new Process1());
        Thread process2 = new Thread(new Process2());
        Thread process3 = new Thread(new Process3());

        // Starting all the threads
        process1.start();
        process2.start();
        process3.start();
    }

    // Process 1 prints "H" and "E"
    static class Process1 implements Runnable {
        public void run() {
            try {
                while (true) {
                    if (printed) return; // Stop execution after HELLO is printed
                    
                    a.acquire(); // Wait for permission to print "H" and "E"
                    System.out.print("H");
                    System.out.print("E");
                    b.release(); // Signal Process 2 to print "L"
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Process 2 prints "L"
    static class Process2 implements Runnable {
        public void run() {
            try {
                while (true) {
                    if (printed) return; // Stop execution after HELLO is printed
                    
                    b.acquire(); // Wait for permission to print "L"
                    System.out.print("L");
                    c.release(); // Signal Process 3 to print "O"
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Process 3 prints "O"
    static class Process3 implements Runnable {
        public void run() {
            try {
                while (true) {
                    if (printed) return; // Stop execution after HELLO is printed
                    
                    c.acquire(); // Wait for permission to print "O"
                    System.out.print("O");
                    a.release(); // Signal Process 1 to start again (loop)
                    
                    // After printing "HELLO", set the flag to true to stop further printing
                    printed = true;
                    break; // Exit the loop and stop all processes
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
