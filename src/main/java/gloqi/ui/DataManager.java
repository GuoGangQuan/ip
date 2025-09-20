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
    private Path appDataDir;
    private Path appDataFile;

    /**
     * Creates a DataManager for a specified data file path.
     * Ensures the directory and file exist.
     *
     * @param dataPath path to the data file
     */
    public DataManager(String dataPath) {
        resolveAppDataPaths(dataPath);
        setupDataFile();
    }
    // ChatGpt was used to help write the following 4 methods.
    private void resolveAppDataPaths(String dataPath) {
        Path path = Path.of(dataPath);
        Path appDir = Path.of(".");
        if (path.getParent() != null) {
            appDir = appDir.resolve(path.getParent());
        }
        this.appDataDir = appDir;
        this.appDataFile = appDir.resolve(path.getFileName());
    }

    private void setupDataFile() {
        createDirectoryIfMissing();
        createFileIfMissing();
    }
    private void createDirectoryIfMissing() {
        try {
            if (!Files.exists(appDataDir)) {
                Files.createDirectories(appDataDir);
                System.out.println("Storage directory created: " + appDataDir);
            }
        } catch (Exception e) {
            System.out.println("Error creating directory: " + e.getMessage());
        }
    }
    private void createFileIfMissing() {
        try {
            if (!Files.exists(appDataFile)) {
                Files.createFile(appDataFile);
                System.out.println("Storage file created: " + appDataFile);
            }
        } catch (Exception e) {
            System.out.println("Error creating file: " + e.getMessage());
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
        if (isFileEmpty()) {
            return new ArrayList<>();
        }
        Object obj = readSerializedObject();
        return validateAndConvert(obj);
    }

    //ChatGpt was used to help write the following 4 method.
    private boolean isFileEmpty() {
        return !this.appDataFile.toFile().exists() || this.appDataFile.toFile().length() == 0;
    }

    private Object readSerializedObject() throws GloqiException {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(this.appDataFile.toFile()))) {
            return ois.readObject();
        } catch (Exception e) {
            throw new GloqiException("Failed to read from file!\nStart up with a fresh file!");
        }
    }

    private ArrayList<Task> validateAndConvert(Object obj) throws GloqiException {
        if (!(obj instanceof ArrayList<?> rawList)) {
            throw new GloqiException("File might be corrupted");
        }

        ArrayList<Task> tasks = new ArrayList<>();
        for (Object o : rawList) {
            tasks.add(validateTask(o));
        }
        return tasks;
    }

    private Task validateTask(Object o) throws GloqiException {
        if (!(o instanceof Task task)) {
            throw new GloqiException("File might be corrupted");
        }
        return task;
    }
}
