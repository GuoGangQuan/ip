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
        ADD;
    }


    public static void main(String[] args) {
        greetMessage();
        Scanner scanInput = new Scanner(System.in);
        String userInput = getInput(scanInput);
        CommandParser commandParser = new CommandParser(userInput);
        Command cmd = commandParser.getCmd();
        BankList bankList = new BankList();
        Task inputTask;
        while (!cmd.equals(Command.BYE)) {
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
                case ADD:
                    inputTask = new Task(userInput);
                    bankList.addTask(inputTask, Gloqi::printInPrompt);
                    break;
            }
            userInput = getInput(scanInput);
            commandParser = new CommandParser(userInput);
            cmd = commandParser.getCmd();
        }
        endMessage();
    }
}