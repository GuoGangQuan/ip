package gloqi.task;

import java.time.LocalDate;

public class Task {
    protected String taskName;
    protected boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName.trim();
        this.isDone = false;
    }

    public Task markDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public String saveFormat() {
        return this.taskName.length()+"|"+this.taskName + "|" + (this.isDone ? "x" : " ");
    }

    public boolean compareDate(LocalDate date) {
        return false;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "x" : " ") + "] " + this.taskName;
    }
}
