import java.util.Scanner;

public class Ui {
    protected final String chatBotName;

    public Ui(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public void greetMessage() {
        printInPrompt("Hello I am " + this.chatBotName + "\nhow can i help you");
    }

    public void endMessage() {
        printInPrompt("Bye, see you next time!");
    }

    public static void printInPrompt(String msg) {
        System.out.println(msg);
        System.out.println("_".repeat(50));
    }

    public static String getInput(Scanner scanIn) {
        System.out.print(">>> ");
        return scanIn.nextLine();
    }
}
