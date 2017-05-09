package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fz on 17-4-15.
 */
public class CacheThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0 ; i < 5 ; i++)
            exec.execute(new LiftOff());
        exec.shutdown();
    }
}
