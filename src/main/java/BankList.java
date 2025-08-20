import java.util.ArrayList;
import java.util.function.Consumer;

public class BankList {
    protected ArrayList<Task> bankList;

    public BankList() {
        this.bankList = new ArrayList<>();
    }

    public void addTask(Task taskName, Consumer<String> printInPrompt) {
        this.bankList.add(taskName);
        taskName.printTask(printInPrompt);
    }

    public void markTask(int index, Consumer<String> printInPrompt) {
        this.bankList.get(index).markDone(true);
        printInPrompt.accept("Nice! I've marked this task as done:\n" + this.bankList.get(index)
                .getPrintableTask());
    }

    public void unmarkTask(int index, Consumer<String> printInPrompt) {
        this.bankList.get(index).markDone(false);
        printInPrompt.accept("OK, I've marked this task as not done yet:\n" + this.bankList.get(index)
                .getPrintableTask());
    }

    public void printList(Consumer<String> printInPrompt) {
        StringBuilder printMsg = new StringBuilder();
        if (!bankList.isEmpty()) {
            for (int i = 0; i < this.bankList.size(); i++) {
                printMsg.append((i + 1)).append(". ").append(bankList.get(i).getPrintableTask()).append("\n");
            }
            printMsg.deleteCharAt(printMsg.length() - 1);
        } else {
            printMsg.append("Task Bank is empty");
        }
        printInPrompt.accept(printMsg.toString());
    }
}
