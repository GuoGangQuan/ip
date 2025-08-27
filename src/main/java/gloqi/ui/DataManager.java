package gloqi.ui;

import gloqi.task.Deadline;
import gloqi.task.Event;
import gloqi.task.Task;
import gloqi.task.Todo;

import java.nio.file.Files;
import java.nio.file.Path;

public class DataManager {
    protected final Path appDataDir;
    protected final Path appDataFile;

    public DataManager(String dataPath) {
        Path path = Path.of(dataPath);
        Path appDir = Path.of(".");
        if (path.getParent() != null) {
            appDir = appDir.resolve(path.getParent());
        }
        this.appDataDir = appDir;
        this.appDataFile = appDir.resolve(path.getFileName());
        setupDataFile();
    }

    private void setupDataFile() {
        try {
            if (!Files.exists(appDataDir)) {
                Files.createDirectories(appDataDir);
                System.out.println("Storage directory created: " + appDataDir);
            }
            if (!Files.exists(appDataFile)) {
                Files.createFile(appDataFile);
                System.out.println("Storage file created: " + appDataFile);
            }
        } catch (Exception e) {
            System.out.println("Error during setup: " + e.getMessage());
        }
    }

    public void writeDataFile(BankList bankList) {
        try {
            Files.write(appDataFile, bankList.SaveBank());
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public BankList loadDataFile() throws GloqiException {
        BankList bankList = new BankList();
        try {
            for (String line : Files.readAllLines(this.appDataFile)) {
                String[] splitLine = line.split("\\|", 2);
                int index = Integer.parseInt(splitLine[0]);
                if (splitLine[1].charAt(index) == '|') {
                    String taskName = splitLine[1].substring(0, index);
                    splitLine = splitLine[1].substring(index + 1).split("\\|", 2);
                    String taskType = splitLine[1].substring(0, 1);
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
            throw new GloqiException("Failed to load tasks (file might be corrupted): " + e.getMessage());
        }
        return bankList;

    }
}
