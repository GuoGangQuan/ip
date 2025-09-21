package gloqi.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gloqi.ui.GloqiException;

/**
 * Represents a task that occurs over a period of time.
 * Extends the Task class with start and end times.
 */
public class Event extends Task {
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    /**
     * Creates a new Event task with a task Description, start time, and end time.
     *
     * @param detail array containing task Description at index 0, start time at index 1, and end time at index 2
     * @throws GloqiException if the date-time format is invalid
     */
    public Event(String[] detail) throws GloqiException {
        super(detail[0]);
        this.startTime = parseDateTime(detail[1].trim(), "from");
        this.endTime = parseDateTime(detail[2].trim(), "to");
    }

    private Event(String taskDescription, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(taskDescription);
        this.startTime = start;
        this.endTime = end;
        this.isDone = isDone;
    }

    @Override
    public Event setDone(boolean isDone) {
        return new Event(this.taskDescription, this.startTime, this.endTime, isDone);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime
                .format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + " to: " + this.endTime
                .format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + ")";
    }

    /**
     * @inheritDoc Checks if the event occurs on the specified date.
     * @Returns true if the date is within the start and end dates (inclusive)
     */
    @Override
    public boolean compareDate(LocalDate date) {
        return (date.isEqual(this.startTime.toLocalDate()) || date.isAfter(this.startTime.toLocalDate()))
                && (date.isEqual(this.endTime.toLocalDate()) || date.isBefore(this.endTime.toLocalDate()));
    }

    private LocalDateTime parseDateTime(String dateTimeStr, String label) throws GloqiException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        try {
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new GloqiException("Invalid date-time format in '" + label
                    + "', Please follow this format: yyyy-MM-dd HHmm");
        }
    }
}
