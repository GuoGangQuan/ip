package gloqi.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import gloqi.task.Task;

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

    public void writeDataFile(ArrayList<Task> bankList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.appDataFile.toFile()))) {
            oos.writeObject(bankList);
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public ArrayList<Task> loadDataFile() throws GloqiException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!this.appDataFile.toFile().exists() || this.appDataFile.toFile().length() == 0) {
            return tasks;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.appDataFile.toFile()))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?> rawList) {
                if (!rawList.isEmpty() && !(rawList.get(0) instanceof Task)) {
                    throw new GloqiException("File might be corrupted");
                }
                tasks = (ArrayList<Task>) rawList;
            } else {
                throw new GloqiException("File might be corrupted");
            }
        } catch (Exception e) {
            throw new GloqiException("Failed to load tasks (file might be corrupted): " + e.getMessage());
        }
        return tasks;

    }
}
