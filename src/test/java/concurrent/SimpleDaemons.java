package concurrent;

import java.util.concurrent.TimeUnit;


/**
 * Created by fz on 17-4-15.
 */
public class SimpleDaemons implements Runnable {

    @Override
    public String toString(){
        return Thread.currentThread().toString();
    }


    @Override
    public void run() {
        try {
        while(true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        }
        catch (InterruptedException e) {
            System.out.print("sleep() interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0 ; i < 10 ; i++){
            Thread demon = new Thread(new SimpleDaemons());
            demon.setDaemon(true);
            demon.start();
        }
        System.out.println("All daemon started");
        TimeUnit.MILLISECONDS.sleep(250);
    }
}
