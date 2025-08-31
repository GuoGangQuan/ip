package gloqi.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import gloqi.task.Task;

/**
 * Manages reading and writing task data to a file.
 * Ensures the storage directory and file exist and handles serialization of tasks.
 */
public class DataManager {
    private final Path appDataDir;
    private final Path appDataFile;

    /**
     * Creates a DataManager for a specified data file path.
     * Ensures the directory and file exist.
     *
     * @param dataPath path to the data file
     */
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

    /**
     * Writes the given list of tasks to the data file.
     *
     * @param bankList list of tasks to save
     */
    public void writeDataFile(ArrayList<Task> bankList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.appDataFile.toFile()))) {
            oos.writeObject(bankList);
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Loads the list of tasks from the data file.
     * Validates each object in the file to ensure it is a Task.
     *
     * @return list of tasks loaded from the file, or empty list if file is empty
     * @throws GloqiException if the file is corrupted or cannot be read
     */
    public ArrayList<Task> loadDataFile() throws GloqiException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!this.appDataFile.toFile().exists() || this.appDataFile.toFile().length() == 0) {
            return tasks;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.appDataFile.toFile()))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?> rawList) {
                for (Object o : rawList) {
                    if (!(o instanceof Task task)) {
                        throw new GloqiException("File might be corrupted");
                    }
                    tasks.add(task);
                }
            } else {
                throw new GloqiException("File might be corrupted");
            }
        } catch (Exception e) {
            throw new GloqiException("Failed to load tasks (file might be corrupted): " + e.getMessage());
        }
        return tasks;

    }
}
