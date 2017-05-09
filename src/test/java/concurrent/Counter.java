package concurrent;

/**
 * Created by fz on 17-4-15.
 */
public class Counter {

    public static int count = 0;

    public static void inc() {
        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }
        count++;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Counter.inc();
                }
            }).start();
        }

        System.out.println("运行结果:concurrent.Counter.count=" + Counter.count);
    }
}