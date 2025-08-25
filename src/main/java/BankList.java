import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BankList {
    protected ArrayList<Task> bankList;

    public BankList() {
        this.bankList = new ArrayList<>();
    }

    public List<String> SaveBank() {
        List<String> lines = new ArrayList<>();
        for (Task task : bankList) {
            lines.add(task.saveFormat());
        }
        return lines;
    }

    public void restoreBank(Task task) {
        this.bankList.add(task);
    }

    public void addTask(Task taskName, Consumer<String> printInPrompt) {
        this.bankList.add(taskName);
        printInPrompt.accept("Got it. I've added this task:\n" + taskName.toString() + "\nNow you have "
                + bankList.size() + " tasks in the bank.");
    }

    public void markTask(int index, Consumer<String> printInPrompt) throws GloqiException {
        if (bankList.size() <= index) {
            throw new GloqiException("your mark number is not in the task bank, check again!");
        }
        this.bankList.get(index).markDone(true);
        printInPrompt.accept("Nice! I've marked this task as done:\n" + this.bankList.get(index)
                .toString());
    }

    public void unmarkTask(int index, Consumer<String> printInPrompt) throws GloqiException {
        if (bankList.size() <= index) {
            throw new GloqiException("your mark number is not in the task bank, check again!");
        }
        this.bankList.get(index).markDone(false);
        printInPrompt.accept("OK, I've marked this task as not done yet:\n" + this.bankList.get(index)
                .toString());
    }

    public void deleteTask(int index, Consumer<String> printInPrompt) throws GloqiException {
        if (bankList.size() <= index || index < 0) {
            throw new GloqiException("This number is not in the task bank, check again!");
        }
        String taskString = this.bankList.get(index).toString();
        this.bankList.remove(index);
        printInPrompt.accept("Following tasks have been deleted:\n" + taskString + "\nNow you have "
                + bankList.size() + " tasks in the bank.");
    }

    public void printList(Consumer<String> printInPrompt) {
        StringBuilder printMsg = new StringBuilder();
        if (!bankList.isEmpty()) {
            for (int i = 0; i < this.bankList.size(); i++) {
                printMsg.append((i + 1)).append(". ").append(bankList.get(i).toString()).append("\n");
            }
            printMsg.deleteCharAt(printMsg.length() - 1);
        } else {
            printMsg.append("Task Bank is empty");
        }
        printInPrompt.accept(printMsg.toString());
    }
}
