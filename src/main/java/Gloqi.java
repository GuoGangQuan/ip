import java.nio.file.Paths;
import java.util.Scanner;

import java.nio.file.Files;
import java.nio.file.Path;

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

    //this method was inspired by chatGPT
    private static void setupDataFile(Path appDataDir, Path appDataFile) {
        try {
            if (!Files.exists(appDataDir)) {
                Files.createDirectories(appDataDir); // Creates all missing parent directories
                System.out.println("Storage directory created: " + appDataDir);
            }
            if (!Files.exists(appDataFile)) {
                Files.createFile(appDataFile); // Creates all missing parent directories
                System.out.println("Storage file created: " + appDataFile);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public static void writeDataFile(Path appDataFile, BankList bankList) {
        try {
            Files.write(appDataFile, bankList.SaveBank());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static BankList loadDataFile(Path appDataFile) {
        BankList bankList = new BankList();
        try {
            for (String line : Files.readAllLines(appDataFile)) {
                String[] splitLine = line.split("\\|", 2);
                int index = Integer.parseInt(splitLine[0]);
                if (splitLine[1].charAt(index) == '|') {
                    String taskName = splitLine[1].substring(0, index);
                    splitLine = splitLine[1].substring(index + 1).split("\\|", 2);
                    String taskType = splitLine[1].substring(0,1);
                    String isDone = splitLine[0];
                    Task restoreTask;
                    switch (taskType) {
                        case "T" -> {
                            restoreTask = new Todo(taskName);
                            restoreTask = restoreTask.setMark(("x".equals(isDone)));
                            bankList.restoreBank(restoreTask);
                        }
                        case "D" -> {
                            restoreTask = new Deadline(new String[]{taskName, splitLine[1].split("\\|", 2)[1]});
                            restoreTask = restoreTask.setMark(("x".equals(isDone)));
                            bankList.restoreBank(restoreTask);
                        }
                        case "E" -> {
                            String[] part = splitLine[1].split("\\|", 3);
                            restoreTask = new Event(new String[]{taskName, part[1], part[2]});
                            restoreTask = restoreTask.setMark(("x".equals(isDone)));
                            bankList.restoreBank(restoreTask);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load tasks (file might be corrupted): " + e.getMessage());
        }
        return bankList;

    }


    public static void main(String[] args) {
        Path appDataDir = Paths.get(".", "data");
        Path appDataFile = appDataDir.resolve("data.txt");
        setupDataFile(appDataDir, appDataFile);
        greetMessage();
        Scanner scanInput = new Scanner(System.in);
        String userInput;
        CommandParser commandParser;
        Command cmd = Command.INVALID;
        BankList bankList = loadDataFile(appDataFile);
        Task inputTask;
        while (!cmd.equals(Command.BYE)) {
            userInput = getInput(scanInput);
            try {
                commandParser = new CommandParser(userInput);
                cmd = commandParser.getCmd();
                switch (cmd) {
                    case LIST:
                        bankList.printList(Gloqi::printInPrompt);
                        break;
                    case MARK:
                        bankList.markTask(commandParser.getIntArg(), Gloqi::printInPrompt);
                        writeDataFile(appDataFile, bankList);
                        break;
                    case UNMARK:
                        bankList.unmarkTask(commandParser.getIntArg(), Gloqi::printInPrompt);
                        writeDataFile(appDataFile, bankList);
                        break;
                    case TODO:
                        inputTask = new Todo(commandParser.getStringArg()[0]);
                        bankList.addTask(inputTask, Gloqi::printInPrompt);
                        writeDataFile(appDataFile, bankList);
                        break;
                    case DEADLINE:
                        inputTask = new Deadline(commandParser.getStringArg());
                        bankList.addTask(inputTask, Gloqi::printInPrompt);
                        writeDataFile(appDataFile, bankList);
                        break;
                    case EVENT:
                        inputTask = new Event(commandParser.getStringArg());
                        bankList.addTask(inputTask, Gloqi::printInPrompt);
                        writeDataFile(appDataFile, bankList);
                        break;
                    case DELETE:
                        bankList.deleteTask(commandParser.getIntArg(), Gloqi::printInPrompt);
                        writeDataFile(appDataFile, bankList);
                }
            } catch (GloqiException e) {
                printInPrompt(e.getMessage());
            }

        }
        endMessage();
    }
}