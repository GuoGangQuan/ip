package gloqi.task;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gloqi.ui.GloqiException;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String[] detail) throws GloqiException {
        super(detail[0]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        try {
            this.by = LocalDateTime.parse(detail[1].trim(), formatter);
        }catch(DateTimeParseException e){
            throw new GloqiException("your date time format is Wrong, Please follow this format: yyyy-MM-dd HHmm");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy h a"))
                + ")";
    }

    @Override
    public String saveFormat() {
        return super.saveFormat() + "|D" + "|" + this.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public boolean compareDate(LocalDate date) {
        return date.isEqual(this.by.toLocalDate());
    }
}
