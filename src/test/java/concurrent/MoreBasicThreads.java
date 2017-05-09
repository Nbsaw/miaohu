package concurrent;

/**
 * Created by fz on 17-4-15.
 */
public class MoreBasicThreads {
    public static void main(String[] args) {
        for(int i = 0 ; i < 5 ; i++)
            new Thread(new LiftOff()).start();
        System.out.println("Waiting for concurrent.LiftOff");
    }
}
