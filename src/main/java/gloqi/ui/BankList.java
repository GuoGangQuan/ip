package gloqi.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import gloqi.task.Task;

/**
 * Manages a list of tasks for the Gloqi chatbot.
 * Provides methods to manage the tasks
 */
public class BankList {
    private final DataManager dataManager;
    private ArrayList<Task> bankLists;

    /**
     * Creates a BankList using the specified DataManager.
     *
     * @param dataManager DataManager for storing tasks
     */
    public BankList(DataManager dataManager) {
        this.bankLists = new ArrayList<>();
        this.dataManager = dataManager;
    }

    /**
     * Adds a task to the bank then prints a confirmation message and save the change to data file.
     *
     * @param taskName task to add
     */
    public String addTask(Task taskName) {
        this.bankLists.add(taskName);
        saveBankList();
        return "1Got it. I've added this task:\n" + taskName.toString() + "\nNow you have "
                + bankLists.size() + " tasks in the bank.";
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index index of the task to mark
     * @throws GloqiException if the index is invalid
     */
    public String markTask(int index) throws GloqiException {
        if (bankLists.size() <= index) {
            throw new GloqiException("your mark number is not in the task bank, check again!");
        }
        bankLists.set(index, bankLists.get(index).setDone(true));
        saveBankList();
        return "Nice! I've marked this task as done:\n" + this.bankLists.get(index).toString();
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index index of the task to unmark
     * @throws GloqiException if the index is invalid
     */
    public String unmarkTask(int index) throws GloqiException {
        if (bankLists.size() <= index) {
            throw new GloqiException("your mark number is not in the task bank, check again!");
        }
        bankLists.set(index, bankLists.get(index).setDone(false));
        saveBankList();
        return "OK, I've marked this task as not done yet:\n" + this.bankLists.get(index).toString();
    }

    /**
     * Deletes the task at the specified index from the bank.
     *
     * @param index index of the task to delete
     * @throws GloqiException if the index is invalid
     */
    public String deleteTask(int index) throws GloqiException {
        if (bankLists.size() <= index || index < 0) {
            throw new GloqiException("This number is not in the task bank, check again!");
        }
        String taskString = this.bankLists.get(index).toString();
        this.bankLists.remove(index);
        saveBankList();
        return "Following tasks have been deleted:\n" + taskString + "\nNow you have "
                + bankLists.size() + " tasks in the bank.";
    }

    /**
     * Prints all tasks in the bank.
     * If the bank is empty, prints a message indicating empty bank.
     */
    public String printList() {
        StringBuilder printMsg = new StringBuilder();
        if (!bankLists.isEmpty()) {
            for (int i = 0; i < this.bankLists.size(); i++) {
                printMsg.append((i + 1)).append(". ").append(bankLists.get(i).toString()).append("\n");
            }
            printMsg.deleteCharAt(printMsg.length() - 1);
        } else {
            printMsg.append("Task Bank is empty");
        }
        return printMsg.toString();
    }

    /**
     * Prints tasks that are available on the specified date.
     *
     * @param date the date to filter tasks by
     */
    public String printList(LocalDate date) {
        StringBuilder printMsg = new StringBuilder();
        printMsg.append("Task available on ").append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")))
                .append(":\n");
        int initLength = printMsg.length();
        if (!bankLists.isEmpty()) {
            for (int i = 0; i < this.bankLists.size(); i++) {
                if (bankLists.get(i).compareDate(date)) {
                    printMsg.append((i + 1)).append(". ").append(bankLists.get(i).toString()).append("\n");
                }
            }
        }
        if (printMsg.length() == initLength) {
            printMsg.append("No tasks found on date ");
            printMsg.append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
        } else {
            printMsg.deleteCharAt(printMsg.length() - 1);
        }
        return printMsg.toString();
    }

    /**
     * Searches for tasks in the bank whose names contain the given keyword or phrase.
     * Prints all matching tasks to the user prompt. If no tasks are found, a message
     * indicating so is displayed.
     *
     * @param userInput the keyword or phrase to search for in task names
     * @throws GloqiException not thrown in current implementation but declared for consistency
     */
    public String findTask(String userInput) throws GloqiException {
        StringBuilder printMsg = new StringBuilder();
        for (int i = 0; i < this.bankLists.size(); i++) {
            if (bankLists.get(i).containTaskName(userInput)) {
                printMsg.append((i + 1)).append(". ").append(bankLists.get(i).toString()).append("\n");
            }
        }
        if (printMsg.isEmpty()) {
            printMsg.append("No tasks found");
        } else {

            printMsg.deleteCharAt(printMsg.length() - 1);
        }
        return printMsg.toString();
    }


    private void saveBankList() {
        this.dataManager.writeDataFile(bankLists);
    }

    /**
     * Loads the bank list from the data file.
     *
     * @return a BankList containing tasks loaded from the file
     * @throws GloqiException if the file is corrupted or cannot be read
     */
    public BankList loadBankList() throws GloqiException {
        BankList bl = new BankList(this.dataManager);
        bl.bankLists = this.dataManager.loadDataFile();
        return bl;
    }
}
