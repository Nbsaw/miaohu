package concurrent;

/**
 * Created by fz on 17-4-15.
 */
public class FibonacciRunnable implements Runnable  {
    private int n;

    // 构造器初始化n成员变量
    public FibonacciRunnable(int n) {
        this.n = n;
    }

    // 费波纳奇数算法
    public int f(int y) {
        return y > 2 ? f(y - 1) + f(y - 2) : 1;
    }

    // 任务
    @Override
    public void run() {
    // TODO Auto-generated method stub
        for (int i = 1; i <= n; i++) {
            System.out.print(f(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
    // 使用线程创建大量的这种任务并驱动它们
        new Thread(new FibonacciRunnable(7)).start();
    }
}
