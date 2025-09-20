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
     * @param dataPath path to the data file eg."data/gloqi.txt"
     */
    public DataManager(String dataPath) throws GloqiException {
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

    private void setupDataFile() throws GloqiException {
        createDirectoryIfMissing();
        createFileIfMissing();
    }

    private void createDirectoryIfMissing() throws GloqiException {
        try {
            if (!Files.exists(appDataDir)) {
                Files.createDirectories(appDataDir);
            }
        } catch (Exception e) {
            throw new GloqiException("Failed to create directory for data file!\n Error:" + e.getMessage());
        }
    }

    private void createFileIfMissing() throws GloqiException {
        try {
            if (!Files.exists(appDataFile)) {
                Files.createFile(appDataFile);
            }
        } catch (Exception e) {
            throw new GloqiException("Failed to create data file!\n Error:" + e.getMessage());
        }
    }

    /**
     * Writes the given list of tasks to the data file.
     *
     * @param bankList list of tasks to save
     */
    public void writeDataFile(ArrayList<Task> bankList) throws GloqiException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.appDataFile.toFile()))) {
            oos.writeObject(bankList);
        } catch (Exception e) {
            throw new GloqiException("Failed to write to file! Changes not saved.\n Error:" + e.getMessage());
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
            throw new GloqiException("No data file found!\nStart up with a fresh file!");
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
            throw new GloqiException("Failed to read from file!\nStart up with a fresh file!");
        }

        ArrayList<Task> tasks = new ArrayList<>();
        for (Object o : rawList) {
            tasks.add(validateTask(o));
        }
        return tasks;
    }

    private Task validateTask(Object o) throws GloqiException {
        if (!(o instanceof Task task)) {
            throw new GloqiException("Failed to read from file!\nStart up with a fresh file!");
        }
        return task;
    }
}
