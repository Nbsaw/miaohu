package concurrent;

import java.util.concurrent.Callable;

/**
 * Created by fz on 17-4-15.
 */
public class FibonacciCallable implements Callable {
    private int n;
    private int sum;
    // 构造器初始化n成员变量
    public FibonacciCallable(int n) {
        this.n = n;
    }

    // 费波纳奇数算法
    public int f(int y) {
        return y > 2 ? f(y - 1) + f(y - 2) : 1;
    }

    @Override
    public Object call() throws Exception {
        for (int i = 1; i <= n; i++) {
            sum += f(i);
        }
        return sum;
    }
}
