package gloqi.task;

/**
 * Represents a "Todo" task
 * Extends the Task class.
 */
public class Todo extends Task {
    /**
     * Creates a new Todo task with the specified name.
     *
     * @param taskName name of the task
     */
    public Todo(String taskName) {
        super(taskName);
    }

    private Todo(String taskName, boolean isDone) {
        super(taskName);
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public Todo setDone(boolean isDone) {
        return new Todo(this.taskName, isDone);
    }


}
