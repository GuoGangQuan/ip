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

    private static Command paserCmd(String userInput) {
        String[] commands = userInput.split(" ", 2);
        String command = commands[0].toLowerCase();
        switch (command) {
            case "list":
                return Command.LIST;
            case "mark":
                return Command.MARK;
            case "unmark":
                return Command.UNMARK;
            case "bye":
                return Command.BYE;
            default:
                return Command.ADD;
        }
    }

    public static void main(String[] args) {
        greetMessage();
        Scanner scanInput = new Scanner(System.in);
        String userInput = getInput(scanInput);
        Command cmd = paserCmd(userInput);
        BankList bankList = new BankList();
        Task inputTask;
        String arg;
        while (!cmd.equals(Command.BYE)) {
            switch (cmd) {
                case LIST:
                    bankList.printList(Gloqi::printInPrompt);
                    break;
                case MARK:
                    bankList.markTask(Integer.parseInt(userInput.split(" ", 2)[1]) - 1,
                            Gloqi::printInPrompt);
                    break;
                case UNMARK:
                    bankList.unmarkTask(Integer.parseInt(userInput.split(" ", 2)[1]) - 1,
                            Gloqi::printInPrompt);
                    break;
                case ADD:
                    inputTask = new Task(userInput);
                    bankList.addTask(inputTask, Gloqi::printInPrompt);
                    break;
            }
            userInput = getInput(scanInput);
            cmd = paserCmd(userInput);
        }
        endMessage();
    }
}