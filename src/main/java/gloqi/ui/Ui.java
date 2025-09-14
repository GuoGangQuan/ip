package gloqi.ui;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import gloqi.task.Task;

/**
 * Handles user interaction for the Gloqi chatbot.
 * Provides methods to display messages and read user input.
 */
public class Ui {
    private final String chatBotName;

    /**
     * Creates a new Ui instance with the specified chatbot name.
     *
     * @param chatBotName name of the chatbot
     */
    public Ui(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    /**
     * the greeting message at the start of the program.
     *
     * @return greeting message
     */
    public String getGreetMessage() {
        return "Hello I am " + this.chatBotName + "\nhow can i help you";
    }

    /**
     * Displays the ending message at the end of the program.
     */
    public String getEndMessage() {
        return "Bye, see you next time!";
    }

    /**
     * Formats a list of tasks into a numbered string representation.
     * Each task is displayed on its own line, prefixed with its index (starting from 1).
     * If the list is empty, returns a message indicating the task bank is empty.
     *
     * @param tasks the list of tasks to format
     * @return a numbered string of tasks, or a message if the list is empty
     */
    public static String formatNumList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "Task Bank is empty";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Formats a list of tasks for display on a specific date.
     * If the list is empty, returns a message indicating no tasks were found on that date.
     * Otherwise, returns a header with the date followed by a numbered list of tasks.
     *
     * @param tasks the list of tasks to format
     * @param date  the date to include in the header or empty message
     * @return a formatted string showing tasks on the specified date, or a message if none exist
     */
    public static String formatShowList(ArrayList<Task> tasks, LocalDate date) {
        if (tasks.isEmpty()) {
            return "No tasks found on date: " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        }
        return "Tasks found on date: " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + "\n"
                + formatNumList(tasks);
    }

    /**
     * Formats a message confirming that a task has been added to the task bank.
     * Includes the task details and the updated total number of tasks.
     *
     * @param task       the task that was added
     * @param totalCount the updated total number of tasks in the bank
     * @return a formatted string confirming the addition of the task
     */
    public static String formatAddMsg(Task task, int totalCount) {
        // Preserves original output prefix "1Got it." exactly as given
        return "Got it. I've added this task:\n" + task + "\nNow you have " + totalCount + " tasks in the bank.";
    }

    /**
     * Formats a message confirming that a task has been marked as done.
     *
     * @param task the task that was marked as done
     * @return a formatted string confirming the task is done
     */
    public static String formatMarkedMsg(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    /**
     * Formats a message confirming that a task has been unmarked (set as not done).
     *
     * @param task the task that was unmarked
     * @return a formatted string confirming the task is not done
     */
    public static String formatUnmarkedMsg(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task;
    }

    /**
     * Formats a message confirming that a task has been deleted from the task bank.
     * Includes the task details and the remaining number of tasks in the bank.
     *
     * @param task      the task that was deleted
     * @param remaining the number of tasks remaining in the bank
     * @return a formatted string confirming the deletion of the task
     */
    public static String formatDeletedMsg(ArrayList<Task> task, int remaining) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tasks have been deleted:\n");
        for (Task value : task) {
            sb.append(value.toString()).append("\n");
        }
        sb.append("\nNow you have ").append(remaining).append(" tasks in the bank.");
        return sb.toString();
    }
}
