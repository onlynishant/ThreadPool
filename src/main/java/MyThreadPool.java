import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by nishantkumar on 27/02/17.
 */

public class MyThreadPool extends Thread {
    private final ArrayBlockingQueue<Callable> taskQueue;
    private final Thread[] pool;
    private static final int MULTIPLICATION_FACTOR = 2;

    private static final int DEFAULT_THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * MULTIPLICATION_FACTOR;
    private static final int DEFAULT_TASK_QUEUE_SIZE = 10;

    private int threadPoolSize;
    private int taskQueueSize;

    public MyThreadPool() {
        this(DEFAULT_TASK_QUEUE_SIZE);
    }

    public MyThreadPool(int queueSize) {
        this(DEFAULT_THREAD_POOL_SIZE, queueSize);
    }

    public MyThreadPool(int poolSize, int queueSize) {
        this.threadPoolSize = poolSize;
        this.taskQueueSize = queueSize;
        this.taskQueue = new ArrayBlockingQueue<>(taskQueueSize);
        this.pool = new Thread[poolSize];

        for (int i = 0; i < threadPoolSize; i++) {
            pool[i] = new Thread(new MyWorker(taskQueue, i));
            pool[i].start();
        }
    }

    public void waitForTermination() {
        try {
            // Join worker threads
            for (Thread thread : pool)
                thread.join();
            // Join master thread
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void submit(Callable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopPool(){
        for(Thread t:pool)
            t.interrupt();
    }
}
