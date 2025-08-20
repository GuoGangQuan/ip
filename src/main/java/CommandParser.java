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
            default:
                this.cmd = Gloqi.Command.ADD;
                break;
        }
    }

    private static Integer getMarkArg(String userInput) {
        String[] commands = userInput.split(" ", 2);
        return Integer.parseInt(commands[1]) - 1;
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
