package interesting;

/**
 * Created by Nbsaw on 17-5-4.
 */
public class LikeBug {
    public static void main(String[] args) {
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        System.out.println(tg.activeCount());
    }
}
