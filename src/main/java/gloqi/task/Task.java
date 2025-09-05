package gloqi.task;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a generic task in the Gloqi chatbot.
 * Stores a task name and its completion status.
 * Can be extended by specific task types like Todo, Deadline, or Event.
 */
public class Task implements Serializable {
    protected String taskName;
    protected boolean isDone;

    /**
     * Creates a new Task with the specified name.
     * The task is initialised to not done.
     *
     * @param taskName name of the task
     */
    public Task(String taskName) {
        this.taskName = taskName.trim();
        this.isDone = false;
    }

    private Task(String taskName, boolean isDone) {
        this.taskName = taskName.trim();
        this.isDone = isDone;
    }

    /**
     * Returns a new Task with the same name and the specified completion status.
     *
     * @param isDone completion status
     * @return new Task with updated status
     */
    public Task setDone(boolean isDone) {
        return new Task(this.taskName, isDone);
    }

    /**
     * Compares the task with a given date.
     * Returns false by default and is overridden by subclasses like Deadline or Event.
     *
     * @param date date to compare
     * @return true if the task occurs on the given date, false otherwise
     */
    public boolean compareDate(LocalDate date) {
        return false;
    }

    public boolean checkContainTaskName(String s) {
        return this.taskName.toLowerCase().contains(s.toLowerCase());
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "x" : " ") + "] " + this.taskName;
    }
}
