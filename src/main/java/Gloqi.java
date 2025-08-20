import java.util.Scanner;

public class Gloqi {

    final static String CHATBOT_NAME = "Gloqi";

    private static void greetMessage() {
        printInPrompt("Hello I am " + CHATBOT_NAME + "\nhow can i help you");
    }

    private static void endMessage() {
        printInPrompt("Bye, see you next time!");
    }

    private static void printInPrompt(String msg) {
        System.out.println(msg);
        System.out.println("_".repeat(50));
    }

    private static String getInput(Scanner scanIn) {
        System.out.print(">>> ");
        return scanIn.nextLine();
    }

    public enum Command {
        LIST,
        MARK,
        UNMARK,
        BYE,
        TODO,
        DEADLINE,
        EVENT,
        INVALID
    }


    public static void main(String[] args) {
        greetMessage();
        Scanner scanInput = new Scanner(System.in);
        String userInput;
        CommandParser commandParser ;
        Command cmd = Command.INVALID;
        BankList bankList = new BankList();
        Task inputTask;
        while (!cmd.equals(Command.BYE)) {
            userInput = getInput(scanInput);
            commandParser = new CommandParser(userInput);
            cmd = commandParser.getCmd();
            switch (cmd) {
                case LIST:
                    bankList.printList(Gloqi::printInPrompt);
                    break;
                case MARK:
                    bankList.markTask(commandParser.getIntArg(), Gloqi::printInPrompt);
                    break;
                case UNMARK:
                    bankList.unmarkTask(commandParser.getIntArg(), Gloqi::printInPrompt);
                    break;
                case TODO:
                    inputTask = new Todo(commandParser.getStringArg()[0]);
                    bankList.addTask(inputTask, Gloqi::printInPrompt);
                    break;
                case DEADLINE:
                    inputTask = new Deadline(commandParser.getStringArg());
                    bankList.addTask(inputTask, Gloqi::printInPrompt);
                    break;
                case EVENT:
                    inputTask = new Event(commandParser.getStringArg());
                    bankList.addTask(inputTask, Gloqi::printInPrompt);
            }

        }
        endMessage();
    }
}