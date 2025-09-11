package gloqi.ui;

/**
 * Handles user interaction for the Gloqi chatbot.
 * Provides methods to display messages and read user input.
 */
public class Ui {
    private final String chatBotName;

    /**
     * Creates a new Ui instance with the specified chatbot name.
     *
     * @param chatBotName name of the chatbot
     */
    public Ui(String chatBotName) {
        this.chatBotName = chatBotName;
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

}
