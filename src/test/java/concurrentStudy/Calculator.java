package concurrentStudy;

import java.util.Date;

/**
 * Created by fz on 17-4-16.
 */
public class Calculator implements Runnable {
    private int number;
    public Calculator(int number){
        this.number = number;
    }
    @Override
    // 如果某个线程调用System.exit(0)指示终结程序，那么全部的线程都会结束执行。
    public void run() {
        for (int i = 0 ; i < 10 ;i++){
            System.out.printf("%s: %d * %d = %d\n",Thread.currentThread().getName(),number,i,i*number);
//            if(i == 4)
//                System.exit(0);
        }
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < 10 ;i++){
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }
}
