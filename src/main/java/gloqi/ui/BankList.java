package gloqi.ui;

import gloqi.task.Task;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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

    public void addTask(Task taskName) {
        this.bankList.add(taskName);
        Ui.printInPrompt("Got it. I've added this task:\n" + taskName.toString() + "\nNow you have "
                + bankList.size() + " tasks in the bank.");
    }

    public void markTask(int index) throws GloqiException {
        if (bankList.size() <= index) {
            throw new GloqiException("your mark number is not in the task bank, check again!");
        }
        this.bankList.get(index).markDone(true);
        Ui.printInPrompt("Nice! I've marked this task as done:\n" + this.bankList.get(index)
                .toString());
    }

    public void unmarkTask(int index) throws GloqiException {
        if (bankList.size() <= index) {
            throw new GloqiException("your mark number is not in the task bank, check again!");
        }
        this.bankList.get(index).markDone(false);
        Ui.printInPrompt("OK, I've marked this task as not done yet:\n" + this.bankList.get(index)
                .toString());
    }

    public void deleteTask(int index) throws GloqiException {
        if (bankList.size() <= index || index < 0) {
            throw new GloqiException("This number is not in the task bank, check again!");
        }
        String taskString = this.bankList.get(index).toString();
        this.bankList.remove(index);
        Ui.printInPrompt("Following tasks have been deleted:\n" + taskString + "\nNow you have "
                + bankList.size() + " tasks in the bank.");
    }

    public void printList() {
        StringBuilder printMsg = new StringBuilder();
        if (!bankList.isEmpty()) {
            for (int i = 0; i < this.bankList.size(); i++) {
                printMsg.append((i + 1)).append(". ").append(bankList.get(i).toString()).append("\n");
            }
            printMsg.deleteCharAt(printMsg.length() - 1);
        } else {
            printMsg.append("Task Bank is empty");
        }
        Ui.printInPrompt(printMsg.toString());
    }

    public void printList(LocalDate date) {
        StringBuilder printMsg = new StringBuilder();
        printMsg.append("Task available on ").append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")))
                .append(":\n");
        int initLength = printMsg.length();
        if (!bankList.isEmpty()) {
            for (int i = 0; i < this.bankList.size(); i++) {
                if (bankList.get(i).compareDate(date)) {
                    printMsg.append((i + 1)).append(". ").append(bankList.get(i).toString()).append("\n");
                }
            }
        }
        if (printMsg.length() == initLength) {
            printMsg.append("No tasks found on date ");
            printMsg.append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
        } else {
            printMsg.deleteCharAt(printMsg.length() - 1);
        }
        Ui.printInPrompt(printMsg.toString());
    }


}
