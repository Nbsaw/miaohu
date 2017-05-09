package concurrent;

/**
 * Created by fz on 17-4-15.
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        System.out.println("Waiting for concurrent.LiftOff");
    }
}
