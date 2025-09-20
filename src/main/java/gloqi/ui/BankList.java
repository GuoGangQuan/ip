package gloqi.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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
     * @param taskDescription task to add
     * @return confirmation message
     */
    public String addTask(Task taskDescription) throws GloqiException {
        this.bankLists.add(taskDescription);
        saveBankList();
        return Ui.formatAddMsg(taskDescription, bankLists.size());
    }

    private void validateIndex(int index) throws GloqiException {
        if (index < 0 || index >= bankLists.size()) {
            throw new GloqiException("Task index " + (index + 1) + " is out of range");
        }
    }

    /**
     * Marks the task at the specified index as done.
     *
     * @param index index of the task to mark
     * @return confirmation message
     * @throws GloqiException if the index is invalid
     */
    public String markTask(int index) throws GloqiException {
        validateIndex(index);
        bankLists.set(index, bankLists.get(index).setDone(true));
        saveBankList();
        return Ui.formatMarkedMsg(this.bankLists.get(index));
    }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param index index of the task to unmark
     * @return confirmation message
     * @throws GloqiException if the index is invalid
     */
    public String unmarkTask(int index) throws GloqiException {
        validateIndex(index);
        bankLists.set(index, bankLists.get(index).setDone(false));
        saveBankList();
        return Ui.formatUnmarkedMsg(this.bankLists.get(index));
    }

    /**
     * Deletes the task at the specified index from the bank.
     * can delete multiple task at on go also
     *
     * @param index indexes of the task to delete
     * @return confirmation message
     * @throws GloqiException if the index is invalid
     */
    public String deleteTask(int[] index) throws GloqiException {
        sortIndicesAsc(index);
        validateIndices(index);
        // store deleted tasks for message printing
        ArrayList<Task> deletedTasks = removeTasks(index);
        // save changes to data file
        saveBankList();
        return Ui.formatDeletedMsg(deletedTasks, this.bankLists.size());
    }

    private void sortIndicesAsc(int[] index) {
        Arrays.sort(index);
    }

    private void validateIndices(int[] index) throws GloqiException {
        for (int i : index) {
            validateIndex(i);
        }
    }

    private ArrayList<Task> removeTasks(int[] index) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = index.length - 1; i >= 0; i--) {
            int j = index[i];
            tasks.add(this.bankLists.get(j));
            this.bankLists.remove(j);
        }
        return tasks;
    }

    /**
     * Prints all tasks in the bank.
     * If the bank is empty, prints a message indicating empty bank.
     *
     * @return formatted list of tasks or empty bank message
     */
    public String printList() {
        String response = Ui.formatNumList(this.bankLists, "Task Bank is empty");
        assert !response.isEmpty() : "List message should not be empty";
        return response;
    }

    /**
     * Prints tasks that are available on the specified date.
     *
     * @param date the date to filter tasks by
     * @return formatted list of tasks on the specified date or a message indicating no tasks found
     */
    public String printList(LocalDate date) {
        ArrayList<Task> filtered = getTasksOnDate(date);
        String response = Ui.formatShowList(filtered, date);
        assert !response.isEmpty() : "List base on date message should not be empty";
        return response;
    }

    private ArrayList<Task> getTasksOnDate(LocalDate date) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : this.bankLists) {
            if (task.compareDate(date)) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Searches for tasks in the bank whose names contain the given keyword or phrase.
     * Prints all matching tasks to the user prompt. If no tasks are found, a message
     * indicating so is displayed.
     *
     * @param userInput the keyword or phrase to search for in task names
     * @return formatted list of matching tasks or a message indicating no matches found
     */
    public String findTask(String userInput) {
        ArrayList<Task> matches = getTasksOnName(userInput);
        String response = Ui.formatNumList(matches, "No matching tasks found for: " + userInput);
        assert !response.isEmpty() : "List base on taskDescription message should not be empty";
        return response;
    }

    private ArrayList<Task> getTasksOnName(String userInput) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : bankLists) {
            if (task.checkContainTaskDescription(userInput)) {
                matches.add(task);
            }
        }
        return matches;
    }


    private void saveBankList() throws GloqiException {
        this.dataManager.writeDataFile(bankLists);
    }

    /**
     * Loads the bank list from the data file.
     *
     * @return a BankList containing tasks loaded from the file
     * @throws GloqiException if the file is corrupted or cannot be read
     */
    public BankList loadBankList() throws GloqiException {
        assert this.dataManager != null : "dataManager should not be null during loading";
        BankList bl = new BankList(this.dataManager);
        bl.bankLists = this.dataManager.loadDataFile();
        assert bl.bankLists != null : "bankLists should not be null during loading";
        return bl;
    }
}
