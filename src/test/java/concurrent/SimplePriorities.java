package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fz on 17-4-15.
 */
public class SimplePriorities implements Runnable {
    private int countDown = 5;
    private volatile double d; // no optimization
    private int priority;

    public SimplePriorities(int priority){
        this.priority = priority;
    }

    @Override
    public String toString(){
        return Thread.currentThread() + ":" +countDown;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        while (true){
            for(int i = 1 ; i < 100000 ; i++) {
                d += (Math.PI + Math.E) / (double) i;
            }
            System.out.println(this);
            if (--countDown == 0) return;
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0 ; i < 5 ; i++)
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        exec.shutdown();
    }
}
