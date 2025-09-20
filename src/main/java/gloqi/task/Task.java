package gloqi.task;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a generic task in the Gloqi chatbot.
 * Stores a task name and its completion status.
 * Can be extended by specific task types like Todo, Deadline, or Event.
 */
public abstract class Task implements Serializable {
    protected String taskDescription;
    protected boolean isDone;

    /**
     * Creates a new Task with the specified name.
     * The task is initialised to not done.
     *
     * @param taskDescription name of the task
     */
    public Task(String taskDescription) {
        this.taskDescription = taskDescription.trim();
        this.isDone = false;
    }

    private Task(String taskDescription, boolean isDone) {
        this.taskDescription = taskDescription.trim();
        this.isDone = isDone;
    }

    /**
     * Returns a new Task with the same name and the specified completion status.
     *
     * @param isDone completion status
     * @return new Task with updated status
     */
    public abstract Task setDone(boolean isDone);

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

    /**
     * Checks if the task name contains the given string, ignoring case.
     *
     * @param s the string to search for within the task name
     * @return {@code true} if the task name contains the string, {@code false} otherwise
     */
    public boolean checkContaintaskDescription(String s) {
        return this.taskDescription.toLowerCase().contains(s.toLowerCase());
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "x" : " ") + "] " + this.taskDescription;
    }
}
