package gloqi.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import gloqi.ui.GloqiException;

/**
 * Parses user input strings into commands and arguments for the Gloqi chatbot.
 * Determines the command type and extracts integer, string, or date arguments as needed.
 */
public class CommandParser {
    private static final String DEADLINE_USAGE = "deadline <task description> /by <date>";
    private static final String COMMAND_SEPARATOR = " ";
    private static final String DEADLINE_KEYWORDS = "/by";
    private static final String[] EVENT_KEYWORDS = new String[]{"/from", "/to"};
    private static final String EVENT_USAGE = "event <task description> /from <date> /to <date>";
    private final Command cmd;
    private int[] intArg;
    private String[] stringArgs;
    private LocalDate dateArg;

    /**
     * Creates a CommandParser by parsing the user input.
     *
     * @param userInput raw input string from the user
     * @throws GloqiException if the input is invalid or incorrectly formatted
     */
    public CommandParser(String userInput) throws GloqiException {
        String command = extractFirstArg(userInput);
        switch (command) {
        case "list" -> this.cmd = Command.LIST;
        case "mark" -> {
            this.cmd = Command.MARK;
            this.intArg = new int[]{getNumArg(userInput)};
        }
        case "unmark" -> {
            this.cmd = Command.UNMARK;
            this.intArg = new int[]{getNumArg(userInput)};
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
            this.intArg = getDeleteNumArg(userInput);
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
        String[] commands = extractNextArg(userInput, COMMAND_SEPARATOR);
        checkNextArgs(commands, "find <search string>");
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
        String[] commands = extractNextArg(userInput, COMMAND_SEPARATOR);
        checkNextArgs(commands, "show <date:yyyy-MM-dd>");
        return parseDate(commands[1]);
    }


    /**
     * Extracts and validates a numeric argument from commands like mark, unmark, delete.
     *
     * @param userInput raw input string
     * @return zero-based integer index of the task
     * @throws GloqiException if the argument is missing or not a valid number
     */
    private int getNumArg(String userInput) throws GloqiException {
        String[] commands = extractNextArg(userInput, COMMAND_SEPARATOR);
        checkNextArgs(commands, "mark/unmark <Task Number>");
        return parseIndex(commands[1]);
    }

    private int[] getDeleteNumArg(String userInput) throws GloqiException {
        String[] commands = extractNextArg(userInput, COMMAND_SEPARATOR);
        checkNextArgs(commands, "delete <Task Number>,<Task Number>");
        return parseMultipleIndex(commands[1]);
    }

    /**
     * Extracts and validates the description for a Todo task.
     *
     * @param userInput raw input string
     * @return the task description
     * @throws GloqiException if the description is missing
     */
    private String getTodoArg(String userInput) throws GloqiException {
        String[] commands = extractNextArg(userInput, COMMAND_SEPARATOR);
        checkNextArgs(commands, "todo <your task>");
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
        String[] commands = extractNextArg(userInput, COMMAND_SEPARATOR);
        checkNextArgs(commands, DEADLINE_USAGE);
        String[] deadlineArgs = extractNextArg(commands[1], DEADLINE_KEYWORDS);
        checkNextArgs(deadlineArgs, DEADLINE_USAGE);
        checkEmptyArgs(deadlineArgs[1], DEADLINE_USAGE, "/by Date");
        checkEmptyArgs(deadlineArgs[0], DEADLINE_USAGE, "task description");
        return new String[]{deadlineArgs[0].trim(), deadlineArgs[1].trim()};
    }

    /**
     * Extracts and validates the description, start date, and end date for an Event task.
     *
     * @param userInput raw input string
     * @return array with task name at index 0, start date at index 1, and end date at index 2
     * @throws GloqiException if description or date keywords (/from, /to) are missing or invalid
     */
    private String[] getEventArg(String userInput) throws GloqiException {
        String[] commands = extractNextArg(userInput, COMMAND_SEPARATOR);
        checkNextArgs(commands, EVENT_USAGE);
        //check on /from keyword
        String[] eventFromArgs = extractNextArg(commands[1], EVENT_KEYWORDS[0]);
        checkNextArgs(eventFromArgs, EVENT_USAGE);
        checkEmptyArgs(eventFromArgs[0], EVENT_USAGE, "task description");
        //check on /to keyword
        String[] eventToArgs = extractNextArg(eventFromArgs[1], EVENT_KEYWORDS[1]);
        checkNextArgs(commands, EVENT_USAGE);
        checkEmptyArgs(eventToArgs[0], EVENT_USAGE, "/from Date");
        checkEmptyArgs(eventToArgs[1], EVENT_USAGE, "/to Date");

        return new String[]{eventFromArgs[0], eventToArgs[0].trim(), eventToArgs[1].trim()};
    }

    public Command getCmd() {
        return this.cmd;
    }

    public int[] getIntArg() {
        return this.intArg;
    }

    public String[] getStringArg() {
        return this.stringArgs;
    }

    public LocalDate getDateArg() {
        return this.dateArg;
    }


    private String extractFirstArg(String userInput) {
        return userInput.split(" ", 2)[0].toLowerCase();
    }

    private String[] extractNextArg(String userInput, String separator) {
        return userInput.split(separator, 2);
    }

    private void checkNextArgs(String[] commands, String usage) throws GloqiException {
        if (commands.length != 2) {
            throw new GloqiException("Missing argument!!! Please follow following format:\n" + usage);
        }
    }

    private void checkEmptyArgs(String arg, String usage, String missingArg) throws GloqiException {
        if (arg.trim().isEmpty()) {
            throw new GloqiException("Missing argument '" + missingArg + "'!!! Please follow following format:\n"
                    + usage);
        }
    }

    private LocalDate parseDate(String input) throws GloqiException {
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            throw new GloqiException("""
                    Date for the show is Invalid!!!Please follow my show format:
                    show <date:yyyy-MM-dd>""");
        }
    }

    private int parseIndex(String userInput) throws GloqiException {
        try {
            int index = Integer.parseInt(userInput) - 1;
            if (index < 0) {
                throw new GloqiException("your mark/unmark/delete number cannot be negative");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new GloqiException("Invalid index!!!Please provide a number.\n");
        }
    }

    private int[] parseMultipleIndex(String userInput) throws GloqiException {
        String[] args = userInput.trim().split(",");
        int[] result = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            result[i] = parseIndex(args[i]);
        }
        return result;
    }

}
