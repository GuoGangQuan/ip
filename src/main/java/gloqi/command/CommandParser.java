package gloqi.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import gloqi.ui.GloqiException;

/**
 * Parses user input strings into commands and arguments for the Gloqi chatbot.
 * Determines the command type and extracts integer, string, or date arguments as needed.
 */
public class CommandParser {
    protected Command cmd;
    protected int intArg;
    protected String[] stringArgs;
    protected LocalDate dateArg;

    /**
     * Creates a CommandParser by parsing the user input.
     *
     * @param userInput raw input string from the user
     * @throws GloqiException if the input is invalid or incorrectly formatted
     */
    public CommandParser(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        String command = commands[0].toLowerCase();
        switch (command) {
        case "list" -> this.cmd = Command.LIST;
        case "mark" -> {
            this.cmd = Command.MARK;
            this.intArg = getNumArg(userInput);
        }
        case "unmark" -> {
            this.cmd = Command.UNMARK;
            this.intArg = getNumArg(userInput);
        }
        case "bye" -> this.cmd = Command.BYE;
        case "todo" -> {
            this.cmd = Command.TODO;
            this.stringArgs = new String[]{getTodoArg(userInput)};
        }
        case "deadline" -> {
            this.cmd = Command.DEADLINE;
            this.stringArgs = getDeadlineArg(userInput);
        }
        case "event" -> {
            this.cmd = Command.EVENT;
            this.stringArgs = getEventArg(userInput);
        }
        case "delete" -> {
            this.cmd = Command.DELETE;
            this.intArg = getNumArg(userInput);
        }
        case "show" -> {
            this.cmd = Command.SHOW;
            this.dateArg = getShowArg(userInput);
        }
        case "find" -> {
            this.cmd = Command.FIND;
            this.stringArgs = new String[]{getFindArg(userInput)};
        }
        default -> this.cmd = Command.INVALID;
        }
    }

    /**
     * Extracts and validates the search string for the "find" command.
     *
     * @param userInput the raw input string from the user
     * @return the search string to look for in task names
     * @throws GloqiException if the search string is missing or the input is invalid
     */
    private String getFindArg(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        if (commands.length != 2) {
            throw new GloqiException("""
                    Search string is Missing!!!Please follow my find format:
                    find <search string>""");
        }
        return commands[1];
    }

    /**
     * Extracts and validates the date argument for the "show" command.
     *
     * @param userInput raw input string
     * @return the LocalDate specified by the user
     * @throws GloqiException if the date is missing or invalid
     */
    private LocalDate getShowArg(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        if (commands.length != 2) {
            throw new GloqiException("""
                    Date for the show is Missing!!!Please follow my show format:
                    show <date:yyyy-MM-dd>""");
        }
        try {
            return LocalDate.parse(commands[1]);
        } catch (DateTimeParseException e) {
            throw new GloqiException("""
                    Date for the show is Invalid!!!Please follow my show format:
                    show <date:yyyy-MM-dd>""");
        }
    }

    /**
     * Extracts and validates a numeric argument from commands like mark, unmark, delete.
     *
     * @param userInput raw input string
     * @return zero-based integer index of the task
     * @throws GloqiException if the argument is missing or not a valid number
     */
    private Integer getNumArg(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        int mark;
        if (commands.length != 2) {
            throw new GloqiException("You need to tell me which task to mark/unmark/delete, cannot be empty");
        }
        try {
            mark = Integer.parseInt(commands[1]) - 1;
            if (mark < 0) {
                throw new GloqiException("your mark/unmark/delete number cannot be negative");
            }
        } catch (NumberFormatException e) {
            throw new GloqiException("you need to tell me the row number of the task you want to mark/unmark/delete");
        }

        return mark;
    }

    /**
     * Extracts and validates the description for a Todo task.
     *
     * @param userInput raw input string
     * @return the task description
     * @throws GloqiException if the description is missing
     */
    private String getTodoArg(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        if (commands.length != 2) {
            throw new GloqiException("""
                    Description for the todo is Missing!!!Please follow my todo format:
                    todo <your task>""");
        }
        return commands[1];
    }

    /**
     * Extracts and validates the description and date for a Deadline task.
     *
     * @param userInput raw input string
     * @return array with task name at index 0 and deadline string at index 1
     * @throws GloqiException if the description or "/by" date is missing or invalid
     */
    private String[] getDeadlineArg(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        String[] deadlineArgs;
        if (commands.length != 2) {
            throw new GloqiException("""
                    Description for the task is Missing!!!Please follow my deadline format:
                    deadline <your task> /by <date>""");
        }
        deadlineArgs = commands[1].split("/by", 2);
        if (deadlineArgs.length != 2) {
            throw new GloqiException("""
                    Wrong!!! I cannot find '/by' keyword. Please follow my deadline format:
                    deadline <your task> /by <date>""");
        } else if (deadlineArgs[1].isEmpty()) {
            throw new GloqiException("""
                    Wrong!!! no Date after '/by' keyword. Please follow my deadline format:
                    deadline <your task> /by <date>""");
        }
        deadlineArgs = new String[]{deadlineArgs[0].trim(), deadlineArgs[1].trim()};
        return deadlineArgs;
    }

    /**
     * Extracts and validates the description, start date, and end date for an Event task.
     *
     * @param userInput raw input string
     * @return array with task name at index 0, start date at index 1, and end date at index 2
     * @throws GloqiException if description or date keywords (/from, /to) are missing or invalid
     */
    private String[] getEventArg(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        if (commands.length != 2) {
            throw new GloqiException("""
                    Description for the task is Missing!!!Please follow my Event format:
                    event <your task> /from <date> /to <date>""");
        }
        String[] eventArgs = new String[3];
        String[] splitArgs = commands[1].split("/from", 2);// taskName
        if (splitArgs.length != 2) {
            throw new GloqiException("""
                    Wrong!!! I cannot find '/from' keyword. Please follow my Event format:
                    event <your task> /from <date> /to <date>""");
        }
        if (splitArgs[0].isEmpty()) {
            throw new GloqiException("""
                    Description for the task is Missing!!!Please follow my Event format:
                    event <your task> /from <date> /to <date>""");
        }
        if (splitArgs[1].isEmpty()) {
            throw new GloqiException("""
                    Wrong!!! Date is Missing after '/from'. Please follow my Event format:
                    event <your task> /from <date> /to <date>""");
        }
        eventArgs[0] = splitArgs[0].trim();
        splitArgs = splitArgs[1].trim().split("/to", 2);
        if (splitArgs.length != 2) {
            throw new GloqiException("""
                    Wrong!!! I cannot find '/to' keyword. Please follow my Event format:
                    event <your task> /from <date> /to <date>""");
        }
        if (splitArgs[0].isEmpty()) {
            throw new GloqiException("""
                    Wrong!!! Date is Missing after '/from'. Please follow my Event format:
                    event <your task> /from <date> /to <date>""");
        }
        if (splitArgs[1].isEmpty()) {
            throw new GloqiException("""
                    Wrong!!! Date is Missing after '/to'. Please follow my Event format:
                    event <your task> /from <date> /to <date>""");
        }
        eventArgs[1] = splitArgs[0].trim();
        eventArgs[2] = splitArgs[1].trim();
        return eventArgs;
    }

    public Command getCmd() {
        return this.cmd;
    }

    public int getIntArg() {
        return this.intArg;
    }

    public String[] getStringArg() {
        return this.stringArgs;
    }

    public LocalDate getDateArg() {
        return this.dateArg;
    }
}
