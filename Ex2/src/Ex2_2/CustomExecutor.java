package Ex2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutor {
    private final ExecutorService executor;
    //    private final PriorityBlockingQueue<Task<?>> queue;
    private int maxPriority;
    private final PriorityBlockingQueue<Runnable> queue;

    CustomExecutor() {
        int numProcessors = Runtime.getRuntime().availableProcessors();
        int corePoolSize = numProcessors / 2;
        int maximumPoolSize = numProcessors - 1;
        long keepAliveTime = 300;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        queue = new PriorityBlockingQueue<>();
        executor = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, queue);
    }


    /**
     * creating a new ThreadPoolExecutor with the Recurments that was asked for
     */
    public static CustomExecutor create() {
        return new CustomExecutor();
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
     * @param operation A Task to execute.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(Callable<T> operation) {
        Task<T> task = Task.createTask(operation);
        updateMaxPriority(task);
        return executor.submit(task);
    }

    /**
     * Submits a task to the ThreadPoolExecutor, with given priority.
     * @param operation  A Task to execute.
     * @param taskType TaskType object that represents the priority of the task.
     * @return a Future representing pending completion of the task.
     */
    public <T> Future<T> submit(TaskType taskType, Callable<T> operation) {
        Task<T> task = Task.createTask(operation, taskType);
        updateMaxPriority(task);
        return executor.submit(task);
    }

    private void updateMaxPriority(Task<?> task) {
        int priority = task.getPriorityValue();
        maxPriority = Math.max(maxPriority, priority);
    }

    public int getMaxPriority() {
        return maxPriority;
    }

    public void shutdown() {
        executor.shutdown();
    }

    public int getCurrentMax() {
        return maxPriority;
    }

    public void gracefullyTerminate() {
        executor.shutdown();
    }
}



/*
create a  CustomExecutor class
- An Executor that asynchronously computes Task instances
- User may submit:
        - A Task instance
        - An operation that may return a value. It will then be used for creating a Task instance
        - An operation that may return a value and a TaskType. It will then be used for creating a
        Task instance
- Consider method chaining for readability and clean code
- The pools queue should maintain elements according to their natural order.
- Unlike Thread scheduling, where threads with higher priority are executed in preference to
        threads with lower priority, ordered data structures maintain elements low natural order
        values in precedence to elements with greater natural order values
        An operation that may run asynchronously with a return value
- Priority in ordered data structures - maintain Task instances according to the integer value of
        the TaskType member
- Maintain the maximum priority of Task instances in the queue at any given time:
        1. Create a method that returns the maximum priority in the queue in O(1) time & space
        complexity
        2. This method may not access the queue to query the current maximum priority
- Set the number of threads to keep in the pool, even if they are idle to be half the number of
        processors available for the Java Virtual Machine (JVM)
- Set the maximum number of threads to allow in the pool to be on the number of processors
        available for the Java Virtual Machine (JVM) minus 1
- when the number of threads is greater than the core, this is the maximum time that excess
        idle threads will wait 300 milliseconds for new tasks before terminating
- After finishing of all tasks submitted to the executor, or if an exception is thrown, terminate
        the executor

 */