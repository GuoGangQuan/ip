import java.util.Scanner;

public class Gloqi {

    private final static String CHATBOT_NAME = "Gloqi";
    private final DataManager dataManager;
    private BankList bankList;
    private final Ui ui;

    public Gloqi(String filePath) {
        this.dataManager = new DataManager(filePath);
        this.ui=new Ui(CHATBOT_NAME);
    }

    public void run(){
        ui.greetMessage();
        Scanner scanInput = new Scanner(System.in);
        String userInput;
        Task inputTask;
        Command cmd = Command.INVALID;
        BankList bankList = new BankList();
        try {
            bankList = dataManager.loadDataFile();
        } catch (GloqiException e) {
            Ui.printInPrompt(e.getMessage());
        }
        while (!cmd.equals(Command.BYE)) {
            userInput = Ui.getInput(scanInput);
            try {
                CommandParser commandParser = new CommandParser(userInput);
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

    public static void main(String[] args) {
        new Gloqi("data/data.txt").run();
    }
}