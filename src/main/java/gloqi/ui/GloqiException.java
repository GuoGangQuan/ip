package gloqi.ui;

/**
 * Represents exceptions that occur in the Gloqi chatbot application.
 * Used to indicate errors such as corrupted data files or invalid commands.
 */
public class GloqiException extends Exception {
    /**
     * Constructs a new GloqiException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public GloqiException(String message) {
        super(message);
    }
}
