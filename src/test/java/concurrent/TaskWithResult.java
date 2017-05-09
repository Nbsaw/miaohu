package concurrent;

import java.util.concurrent.Callable;

/**
 * Created by fz on 17-4-15.
 */
public class TaskWithResult implements Callable {
    private int id;
    public TaskWithResult(int id){
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "result of concurrent.TaskWithResult" + id;
    }
}
