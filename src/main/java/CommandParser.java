public class CommandParser {
    protected Gloqi.Command cmd;
    protected int intArg;
    protected String[] stringArg;

    public CommandParser(String userInput) {
        String[] commands = userInput.split(" ", 2);
        String command = commands[0].toLowerCase();
        switch (command) {
            case "list":
                this.cmd = Gloqi.Command.LIST;
                break;
            case "mark":
                this.cmd = Gloqi.Command.MARK;
                this.intArg = getMarkArg(userInput);
                break;
            case "unmark":
                this.cmd = Gloqi.Command.UNMARK;
                this.intArg = getMarkArg(userInput);
                break;
            case "bye":
                this.cmd = Gloqi.Command.BYE;
                break;
            case "todo":
                this.cmd = Gloqi.Command.TODO;
                this.stringArg = new String[]{getTodoArg(userInput)};
                break;
            case "deadline":
                this.cmd = Gloqi.Command.DEADLINE;
                this.stringArg = getDeadlineArg(userInput);
                break;
            case "event":
                this.cmd = Gloqi.Command.EVENT;
                this.stringArg = getEventArg(userInput);
                break;
            default:
                this.cmd = Gloqi.Command.INVALID;
        }
    }

    private static Integer getMarkArg(String userInput) {
        String[] commands = userInput.split(" ", 2);
        return Integer.parseInt(commands[1]) - 1;
    }

    private static String getTodoArg(String userInput) {
        String[] commands = userInput.split(" ", 2);
        return commands[1];
    }

    private static String[] getDeadlineArg(String userInput) {
        String[] commands = userInput.split(" ", 2);
        return commands[1].split("/by", 2);
    }

    private static String[] getEventArg(String userInput) {
        String[] commands = userInput.split(" ", 2);
        String[] eventArgs = new String[3];
        eventArgs[0] = commands[1].split("/from", 2)[0];// taskName
        eventArgs[1] = commands[1].split("/from", 2)[1].split("/to", 2)[0];// startTime
        eventArgs[2] = commands[1].split("/from", 2)[1].split("/to", 2)[1];// endTime
        return eventArgs;
    }

    public Gloqi.Command getCmd() {
        return this.cmd;
    }

    public int getIntArg() {
        return this.intArg;
    }

    public String[] getStringArg() {
        return this.stringArg;
    }
}
