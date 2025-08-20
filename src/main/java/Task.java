import java.util.function.Consumer;

public class Task {
    protected String taskName;
    protected boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public Task newTask(String taskName) {
        return new Task(taskName);
    }

    public void markDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getPrintableTask() {
        return "[" + (this.isDone ? "x" : " ") + "] " + this.taskName;
    }

    public void printTask(Consumer<String> printInPrompt) {
        printInPrompt.accept("added: " + this.taskName);
    }
}
