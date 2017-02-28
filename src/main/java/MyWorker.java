import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by nishantkumar on 27/02/17.
 */
class MyWorker implements Runnable {
    private final int threadId;
    private ArrayBlockingQueue<Callable> taskQueue;

    // Default read timeout is 1s
    private volatile long queueReadTimeout = 1;
    private volatile TimeUnit queueReadTimeoutUnit = TimeUnit.SECONDS;

    MyWorker(ArrayBlockingQueue<Callable> taskQueue, int threadID) {
        this.threadId = threadID;
        this.taskQueue = taskQueue;
    }

    public void run() {
        Callable task;
        while (true) {
            try {
                task = taskQueue.poll(queueReadTimeout, queueReadTimeoutUnit);
                if (task != null) {
                    task.call();
                }
            } catch (InterruptedException e) {
                System.out.println("Got interrupt signal. Thread ID: " + threadId);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}