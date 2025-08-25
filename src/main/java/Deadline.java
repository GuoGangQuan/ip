import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String[] detail) {
        super(detail[0]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        this.by = LocalDateTime.parse(detail[1].trim(), formatter);
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
}
