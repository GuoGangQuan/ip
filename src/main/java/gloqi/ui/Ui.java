package gloqi.ui;

import java.util.Scanner;

/**
 * Handles user interaction for the Gloqi chatbot.
 * Provides methods to display messages and read user input.
 */
public class Ui {
    private final String chatBotName;
    private final Scanner scanInput;

    /**
     * Creates a new Ui instance with the specified chatbot name.
     *
     * @param chatBotName name of the chatbot
     */
    public Ui(String chatBotName) {
        this.chatBotName = chatBotName;
        this.scanInput = new Scanner(System.in);
    }

    /**
     * Displays the greeting message at the start of the program.
     */
    public void getGreetMessage() {
        printInPrompt("Hello I am " + this.chatBotName + "\nhow can i help you");
    }

    /**
     * Displays the ending message at the end of the program.
     */
    public void getEndMessage() {
        printInPrompt("Bye, see you next time!");
    }

    /**
     * Prints message in predefine format
     *
     * @param msg message to display
     */
    public static void printInPrompt(String msg) {
        System.out.println(msg);
        System.out.println("_".repeat(50));
    }

    /**
     * Prompts the user with ">>>" and reads a line of input from the console.
     *
     * @return the input string entered by the user
     */
    public String getInput() {
        System.out.print(">>> ");
        return this.scanInput.nextLine();
    }
}
