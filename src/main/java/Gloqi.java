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

    public static void main(String[] args) {
        greetMessage();
        Scanner scanInput = new Scanner(System.in);
        String userInput = getInput(scanInput);
        BankList bankList = new BankList();
        while (!userInput.equalsIgnoreCase("bye")) {
            if (userInput.equalsIgnoreCase("list")) {
                bankList.printList(Gloqi::printInPrompt);
            } else {
                printInPrompt(userInput);
                bankList.addTask(userInput);
            }
            userInput = getInput(scanInput);
        }
        endMessage();
    }
}