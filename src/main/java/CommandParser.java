public class CommandParser {
    protected Command cmd;
    protected int intArg;
    protected String[] stringArg;

    public CommandParser(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        String command = commands[0].toLowerCase();
        switch (command) {
            case "list":
                this.cmd = Command.LIST;
                break;
            case "mark":
                this.cmd = Command.MARK;
                this.intArg = getMarkArg(userInput);
                break;
            case "unmark":
                this.cmd = Command.UNMARK;
                this.intArg = getMarkArg(userInput);
                break;
            case "bye":
                this.cmd = Command.BYE;
                break;
            case "todo":
                this.cmd = Command.TODO;
                this.stringArg = new String[]{getTodoArg(userInput)};
                break;
            case "deadline":
                this.cmd = Command.DEADLINE;
                this.stringArg = getDeadlineArg(userInput);
                break;
            case "event":
                this.cmd = Command.EVENT;
                this.stringArg = getEventArg(userInput);
                break;
            case "delete":
                this.cmd = Command.DELETE;
                this.intArg = getMarkArg(userInput);
                break;
            default:
                throw new GloqiException("""
                        Invalid command, only following commands are supported:
                        list,mark,unmark,bye,deadline,event,todo""");
        }
    }

    private Integer getMarkArg(String userInput) throws GloqiException {
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

    private String getTodoArg(String userInput) throws GloqiException {
        String[] commands = userInput.split(" ", 2);
        if (commands.length != 2) {
            throw new GloqiException("The todo description cannot be empty, try again");
        }
        return commands[1];
    }

    private String[] getDeadlineArg(String userInput) {
        String[] commands = userInput.split(" ", 2);
        return commands[1].split("/by", 2);
    }

    private String[] getEventArg(String userInput) {
        String[] commands = userInput.split(" ", 2);
        String[] eventArgs = new String[3];
        eventArgs[0] = commands[1].split("/from", 2)[0];// taskName
        eventArgs[1] = commands[1].split("/from", 2)[1].split("/to", 2)[0];// startTime
        eventArgs[2] = commands[1].split("/from", 2)[1].split("/to", 2)[1];// endTime
        return eventArgs;
    }

    public Command getCmd() {
        return this.cmd;
    }

    public int getIntArg() {
        return this.intArg;
    }

    public String[] getStringArg() {
        return this.stringArg;
    }
}
