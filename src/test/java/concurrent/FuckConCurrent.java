package concurrent;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by fz on 17-4-16.
 */

class Incr implements Runnable{
    private static int i = 0;
    @Override
    public void run() {
        for (int j = 0; j < 100 ; j++){
            if (j % 5 == 0){
                Thread.yield();
            }
            if (i < 100){
                System.out.println(Thread.currentThread() + ":" + i++);
            }
        }
    }
}

class ADaemon implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("Starting Adaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("This should always run ?");
        }
    }
}


class SimpleThread extends Thread{
    private int countDown = 5;
    private static int threadCount = 0;
    public SimpleThread(){
        super(Integer.toString(++threadCount));
        start();
    }

    @Override
    public String toString() {
        return "#" + getName() + "(" + countDown + ") " + new Date().getTime();
    }

    @Override
    public void run() {
        while (true){
            System.out.println(this);
            if (--countDown == 0)
                return;
        }
    }
}


public class FuckConCurrent {
    // 直接集成Thread和Runnable的区别就是,后者可以继承另一个不用的类而Thread不行

    //

    @Test
    // 时而五个线程,时而四个,两个...
    public void SimpleThreadTest() throws InterruptedException {
        for(int i = 0 ; i < 5 ;i++){
            new SimpleThread();
        }
    }

    @Test
    public void IncrTest() throws InterruptedException {
        // 设置优先级效果不明显,但是不设置的话优先级貌似默认是5
        // 采用优先级进行相对调度,相比优先级高的抢占资源的概率要高一些，同样的优先级在前面的调度的更快
        Thread t = new Thread(new Incr());
        Thread t2 = new Thread(new Incr());
        t.start();
        t2.start();
        Thread.sleep(10);
    }

    @Test
    // 书里面说把Daemon设置为true,将会输出Starting Adaemon
    // 不设置为true,将会输出Starting Adaemon,This should always run ?
    // 给我的感觉就是Daemon设置为true,将不会执行Finally块的语句
    // 然而我没有设置为true,也没有输出
    // 于是我怀疑是不是根本还没执行到Finally , main线程就结束了
    // 然后我就加了一条sleep,查看
    // 果然是这样... 不关设置没设置Daemon都会输出Finally语句
    public void AdaemonTest() throws InterruptedException{
        Thread t = new Thread(new ADaemon());
//        t.setDaemon(true);
        t.start();
        Thread.sleep(1000);
    }
}
