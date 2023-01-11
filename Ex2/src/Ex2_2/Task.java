package Ex2_2;

import java.util.concurrent.Callable;

public class Task<T> implements Callable<T>, Comparable<Task<T>> {
    private final TaskType tasktype;
    private final Callable<T> called;

    /**
     * constructor that take
     * @param tasktype
     * @param called
     * */
    private Task(TaskType tasktype, Callable<T> called) {
        this.tasktype = tasktype;
        this.called = called;
    }

    /**
     * Computes a result, or throws an exception if unable to do it
     * @return any type of callable
     * */
    @Override
    public T call() throws Exception {
        return called.call();
    }

    /**
     * Creates a new task with priority
     * @param called  Callable method of task
     * @param tasktype Task priority
     * @return a Task of any type
     */
    public static <T> Task<T> createTask(Callable<T> called, TaskType tasktype) {
        return new Task<>(tasktype, called);
    }

    /**
     * Creates a new task, with normal priority
     * @param called  Callable method of task
     * @return a Task of any type
     */
    public static <T> Task<T> createTask(Callable<T> called) {
        return new Task<>(TaskType.OTHER, called);
    }

    public int getPriorityValue() {
        return tasktype.getPriorityValue();
    }

    /**
     * Compares the priority of this task to other task's priority
     * @param other the other task that we are comparing to.
     * @return a number from -1 to 1 that tell us if our task is bigger or smaller or equal that the other task
     *
     */
    @Override
    public int compareTo(Task<T> other) {
        return Integer.compare(getPriorityValue(), other.getPriorityValue());
    }


}

