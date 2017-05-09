package concurrent;

/**
 * Created by fz on 17-4-15.
 */
public class RunnableDemo implements Runnable {
    private String startMsg;
    private String endMsg;


    public RunnableDemo(String startMsg, String endMsg) {
        super();
        this.startMsg = startMsg;
        this.endMsg = endMsg;

    }


    @Override

    public void run() {
        System.out.println(this + startMsg + " start");
        for (int i = 0; i < 3; i++) {
            System.out.println("run " + i);
            Thread.yield();
        }
        System.out.println(this + endMsg + " end");

    }

    public static void main(String[] args) {
        RunnableDemo demo = new RunnableDemo("a", "b");
        Thread thread1 = new Thread(demo);
        thread1.start();
        Thread thread2 = new Thread(demo);
        thread2.start();
        Thread thread3 = new Thread(demo);
        thread3.start();
    }
}