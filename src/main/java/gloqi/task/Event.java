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
     * Creates a new Event task with a task name, start time, and end time.
     *
     * @param detail array containing task name at index 0, start time at index 1, and end time at index 2
     */
    public Event(String[] detail) throws GloqiException {
        super(detail[0]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        try {
            this.startTime = LocalDateTime.parse(detail[1].trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new GloqiException("Invalid date-time format in 'from', Please follow this format: yyyy-MM-dd HHmm");
        }
        try {
            this.endTime = LocalDateTime.parse(detail[2].trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new GloqiException("Invalid date-time format in 'to', Please follow this format: yyyy-MM-dd HHmm");
        }
    }

    private Event(String taskName, LocalDateTime start, LocalDateTime end, boolean isDone) {
        super(taskName);
        this.startTime = start;
        this.endTime = end;
        this.isDone = isDone;
    }

    @Override
    public Event setDone(boolean isDone) {
        return new Event(this.taskName, this.startTime, this.endTime, isDone);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime
                .format(DateTimeFormatter.ofPattern("MMM dd yyyy h a")) + " to: " + this.endTime
                .format(DateTimeFormatter.ofPattern("MMM dd yyyy h a")) + ")";
    }

    /**
     * @inheritDoc Checks if the event occurs on the specified date.
     * Returns true if the date is within the start and end dates (inclusive).
     */
    @Override
    public boolean compareDate(LocalDate date) {
        return (date.isEqual(this.startTime.toLocalDate()) || date.isAfter(this.startTime.toLocalDate()))
                && (date.isEqual(this.endTime.toLocalDate()) || date.isBefore(this.endTime.toLocalDate()));
    }
}
