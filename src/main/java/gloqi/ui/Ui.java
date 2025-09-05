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
     * Prints message in predefine format
     *
     * @param msg message to display
     */
    public static String printInPrompt(String msg) {
        System.out.println(msg);
        System.out.println("_".repeat(50));
        return msg;
    }

    /**
     * the greeting message at the start of the program.
     *
     * @return greeting message
     */
    public String getGreetMessage() {
        return "Hello I am " + this.chatBotName + "\nhow can i help you";
    }

    /**
     * Displays the ending message at the end of the program.
     */
    public String getEndMessage() {
        return "Bye, see you next time!";
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
