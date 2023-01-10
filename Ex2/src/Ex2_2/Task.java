package Ex2_2;

import java.util.concurrent.Callable;

public class Task<T> implements Callable<T>, Comparable<Task<T>> {
    private final TaskType taskType;
    private final Callable<T> operation;

    private Task(TaskType taskType, Callable<T> operation) {
        this.taskType = taskType;
        this.operation = operation;
    }

    /**
     * Creates a new task, with default priority.
     * @param operation  Callable method of task
     * @return a Task object of any type.
     */
    public static <T> Task<T> createTask(Callable<T> operation) {
        return new Task<>(TaskType.OTHER, operation);
    }

    /**
     * Creates a new task with priority
     * @param operation  Callable method of task to do.
     * @param taskType Task priority.
     * @return a Task object of any type
     */
    public static <T> Task<T> createTask(Callable<T> operation, TaskType taskType) {
        return new Task<>(taskType, operation);
    }

    @Override
    public T call() throws Exception {
        return operation.call();
    }

    public int getPriorityValue() {
        return taskType.getPriorityValue();
    }

    public TaskType getType() {
        return taskType;
    }

    /**
     * Compares the priority of this task to other task's priority.  Returns a
     * negative integer, zero, or a positive integer as the priority of
     * this task is less than, equal to, or greater than the specified priority
     * of other task's.
     *
     * @param other the other task that we are comparing to.
     *
     * @return a negative integer, zero, or a positive integer as the priority of
     * this task is less than, equal to, or greater than the specified priority
     * of other task's.
     *
     */
    @Override
    public int compareTo(Task<T> other) {
        return Integer.compare(getPriorityValue(), other.getPriorityValue());
    }
}





//
//create a Task class:
//- Represents a task with a TaskType and may return a value of some type
//- It may throw an exception if unable to compute the result
//- instances are potentially executed by another thread in one of the threads in CustomExecutor
//- It may be submitted to the queue in CustomExecutor
//- The TaskType member is used to return an integer value representing the instances priority
//- Exposes public factory methods for safe creation
// Creation of a Task instance requires passing at least:
//        An operation that may run asynchronously with a return value
//- It is also possible to pass A TaskType as argument for instance creation.
//- Natural order for Task instances ought to be determined by the priority of the TaskType
//        member when using ordered data structures
