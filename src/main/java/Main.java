import java.util.concurrent.Callable;

/**
 * Created by nishantkumar on 27/02/17.
 */

class MyCall implements Callable {
    public Object call() throws Exception {
        System.out.println("in callable");
        return null;
    }
}

public class Main {

    public static void main(String[] args) {

        // Create thread pool
        MyThreadPool pool = new MyThreadPool();

        // Create new callable task
        MyCall c1 = new MyCall();

        // Add new task
        pool.submit(c1);

        // Create new callable task again
        MyCall c2 = new MyCall();

        // Add new task again
        pool.submit(c2);

        // wait for termination of all thread
        //pool.waitForTermination();

        // Stop pool
        pool.stopPool();

        System.out.println("end");
    }
}


