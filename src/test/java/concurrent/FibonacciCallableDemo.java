package concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fz on 17-4-15.
 */
public class FibonacciCallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0 ; i < 10 ; i++)
            System.out.println(exec.submit(new FibonacciCallable(i)).get());
    }
}
