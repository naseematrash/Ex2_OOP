package Ex2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutor {
    private final ExecutorService executor;

    private int maxPriority;

    private final PriorityBlockingQueue<Runnable> queue;


    public CustomExecutor() {
         int numProcessors = Runtime.getRuntime().availableProcessors();
         int corePoolSize = numProcessors / 2;
         int maximumPoolSize = numProcessors - 1;
        long  keepAliveTime = 300;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        queue = new PriorityBlockingQueue<>();
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, queue);
    }


    private void updateMaxPriority(Task<?> task) {
        int priority = task.getPriorityValue();
        maxPriority = Math.max(maxPriority, priority);
    }


    /**
     * Submits a task to the ThreadPoolExecutor.
     * @param task  A Task to execute.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(Task<T> task) {
        updateMaxPriority(task);
        return executor.submit(task);
    }

    /**
     * Submits a task to the ThreadPoolExecutor, without priority.
     * @param called A Task to execute.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(Callable<T> called) {
        Task<T> task = Task.createTask(called);
        updateMaxPriority(task);
        return executor.submit(task);
    }

    /**
     * Submits a task to the ThreadPoolExecutor, with given priority.
     * @param called  A Task to execute.
     * @param taskType TaskType object that represents the priority of the task.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(TaskType taskType, Callable<T> called) {
        Task<T> task = Task.createTask(called, taskType);
        updateMaxPriority(task);
        return executor.submit(task);
    }

    public int getCurrentMax() {
        return maxPriority;
    }

    public void gracefullyTerminate() {
        executor.shutdown();
    }
}