package gloqi.ui;

import java.util.Scanner;

public class Ui {
    protected final String chatBotName;
    private final Scanner scanInput;

    public Ui(String chatBotName) {
        this.chatBotName = chatBotName;
        this.scanInput = new Scanner(System.in);
    }

    public static void printInPrompt(String msg) {
        System.out.println(msg);
        System.out.println("_".repeat(50));
    }

    public void greetMessage() {
        printInPrompt("Hello I am " + this.chatBotName + "\nhow can i help you");
    }

    public void endMessage() {
        printInPrompt("Bye, see you next time!");
    }

    public String getInput() {
        System.out.print(">>> ");
        return this.scanInput.nextLine();
    }
}
