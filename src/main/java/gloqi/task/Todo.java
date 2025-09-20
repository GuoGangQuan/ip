package gloqi.task;

/**
 * Represents a "Todo" task
 * Extends the Task class.
 */
public class Todo extends Task {
    /**
     * Creates a new Todo task with the specified name.
     *
     * @param taskDescription name of the task
     */
    public Todo(String taskDescription) {
        super(taskDescription);
    }

    private Todo(String taskDescription, boolean isDone) {
        super(taskDescription);
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public Todo setDone(boolean isDone) {
        return new Todo(this.taskDescription, isDone);
    }


}
