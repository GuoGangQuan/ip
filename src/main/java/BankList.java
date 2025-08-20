import java.util.ArrayList;
import java.util.function.Consumer;

public class BankList {
    private final ArrayList<String> bankList;

    BankList() {
        this.bankList = new ArrayList<>();
    }

    public void addTask(String taskName) {
        this.bankList.add(taskName);
    }

    public void printList(Consumer<String> printInPrompt) {
        StringBuilder printMsg = new StringBuilder();
        if (!bankList.isEmpty()) {
            for (int i = 0; i < this.bankList.size(); i++) {
                printMsg.append((i + 1)).append(". ").append(bankList.get(i)).append("\n");
            }
            printMsg.deleteCharAt(printMsg.length() - 1);
        } else {
            printMsg.append("Task Bank is empty");
        }
        printInPrompt.accept(printMsg.toString());
    }
}
