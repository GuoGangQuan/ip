package gloqi.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gloqi.ui.GloqiException;

/**
 * Represents a task that has a deadline by a specific date and time.
 * Extends the Task class.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Creates a new Deadline task with a name and deadline.
     *
     * @param detail array containing task name at index 0 and deadline string at index 1
     * @throws GloqiException if the date-time format is invalid
     */
    public Deadline(String[] detail) throws GloqiException {
        super(detail[0]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        try {
            this.by = LocalDateTime.parse(detail[1].trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new GloqiException("your date time format is Wrong, Please follow this format: yyyy-MM-dd HHmm");
        }
    }

    private Deadline(String taskName, LocalDateTime by, boolean isDone) {
        super(taskName);
        this.by = by;
        this.isDone = isDone;
    }

    @Override
    public Deadline hasDone(boolean isDone) {
        return new Deadline(this.taskName, this.by, isDone);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy h a"))
                + ")";
    }

    /**
     * @inheritDoc Deadline task will check if the input data is equal to task date
     */
    @Override
    public boolean compareDate(LocalDate date) {
        return date.isEqual(this.by.toLocalDate());
    }
}
