import java.util.Scanner;

/* Example of Multithreading provided by Professor Ishigaki in class.
 * This class runs two threads that perform different tasks at the same time.
 */
public class Threading {
    // The main method creates a new thread and then proceeds to perform
    // its own tasks.
    public static void main(String[] args) {
        // Second thread created and started
        AnotherThread at = new AnotherThread();
        at.start();

        // Continue to read in and print out input from the user.
        while (true) {
            System.out.println("write a message");
            Scanner myObj = new Scanner(System.in);
            String chat = myObj.nextLine();
            System.out.println(chat);
        }
    }

    // This class is a subclass of Thread. When an instance of this class is
    // started, it prints out a statement and then waits a second on loop.
    static public class AnotherThread extends Thread {
        public void run() {
            while (true) {
                System.out.println("doing another task");

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

