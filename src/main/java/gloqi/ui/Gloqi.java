package gloqi.ui;

import gloqi.command.Command;
import gloqi.command.CommandParser;
import gloqi.task.Deadline;
import gloqi.task.Event;
import gloqi.task.Task;
import gloqi.task.Todo;

public class Gloqi {

    private final static String CHATBOT_NAME = "Gloqi";
    private final Ui ui;
    private BankList bankList;

    public Gloqi(String filePath) {
        this.bankList = new BankList(new DataManager(filePath));
        this.ui = new Ui(CHATBOT_NAME);
    }

    public static void main(String[] args) {
        new Gloqi("data/data.txt").run();
    }

    public void run() {
        ui.greetMessage();
        String userInput;
        Task inputTask;
        Command cmd = Command.INVALID;
        try {
            bankList = bankList.loadBankList();
        } catch (GloqiException e) {
            Ui.printInPrompt(e.getMessage());
        }
        while (!cmd.equals(Command.BYE)) {
            userInput = ui.getInput();
            try {
                CommandParser commandParser = new CommandParser(userInput);
                cmd = commandParser.getCmd();
                switch (cmd) {
                case LIST -> bankList.printList();
                case MARK -> {
                    bankList.markTask(commandParser.getIntArg());
                }
                case UNMARK -> {
                    bankList.unmarkTask(commandParser.getIntArg());
                }
                case TODO -> {
                    inputTask = new Todo(commandParser.getStringArg()[0]);
                    bankList.addTask(inputTask);
                }
                case DEADLINE -> {
                    inputTask = new Deadline(commandParser.getStringArg());
                    bankList.addTask(inputTask);
                }
                case EVENT -> {
                    inputTask = new Event(commandParser.getStringArg());
                    bankList.addTask(inputTask);
                }
                case DELETE -> {
                    bankList.deleteTask(commandParser.getIntArg());
                }
                case SHOW -> bankList.printList(commandParser.getDateArg());
                }
            } catch (GloqiException e) {
                Ui.printInPrompt(e.getMessage());
            }

        }
        ui.endMessage();
    }
}