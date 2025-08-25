import java.util.function.Consumer;

public class Task {
    protected String taskName;
    protected boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public void markDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String saveFormat() {
        return this.taskName.length()+"|"+this.taskName + "|" + (this.isDone ? "x" : " ");
    }
    public Task setMark(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "x" : " ") + "] " + this.taskName;
    }
}
