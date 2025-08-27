import java.nio.file.Paths;
import java.util.Scanner;

import java.nio.file.Path;

public class Gloqi {

    final static String CHATBOT_NAME = "Gloqi";

    public static void main(String[] args) {
        DataManager dataManager= new DataManager("data/data.txt");
        Ui ui = new Ui(CHATBOT_NAME);
        ui.greetMessage();
        Scanner scanInput = new Scanner(System.in);
        String userInput;
        CommandParser commandParser;
        Command cmd = Command.INVALID;
        BankList bankList = new BankList();
        try {
            bankList = dataManager.loadDataFile();
        } catch (GloqiException e) {
            Ui.printInPrompt(e.getMessage());
        }
        Task inputTask;
        while (!cmd.equals(Command.BYE)) {
            userInput = Ui.getInput(scanInput);
            try {
                commandParser = new CommandParser(userInput);
                cmd = commandParser.getCmd();
                switch (cmd) {
                    case LIST -> bankList.printList();
                    case MARK -> {
                        bankList.markTask(commandParser.getIntArg());
                        dataManager.writeDataFile(bankList);
                    }
                    case UNMARK -> {
                        bankList.unmarkTask(commandParser.getIntArg());
                        dataManager.writeDataFile(bankList);
                    }
                    case TODO -> {
                        inputTask = new Todo(commandParser.getStringArg()[0]);
                        bankList.addTask(inputTask);
                        dataManager.writeDataFile(bankList);
                    }
                    case DEADLINE -> {
                        inputTask = new Deadline(commandParser.getStringArg());
                        bankList.addTask(inputTask);
                        dataManager.writeDataFile(bankList);
                    }
                    case EVENT -> {
                        inputTask = new Event(commandParser.getStringArg());
                        bankList.addTask(inputTask);
                        dataManager.writeDataFile(bankList);
                    }
                    case DELETE -> {
                        bankList.deleteTask(commandParser.getIntArg());
                        dataManager.writeDataFile(bankList);
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