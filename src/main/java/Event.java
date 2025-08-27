import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    public Event(String[] detail) {
        super(detail[0]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        this.startTime = LocalDateTime.parse(detail[1].trim(), formatter);
        this.endTime = LocalDateTime.parse(detail[2].trim(), formatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime
                .format(DateTimeFormatter.ofPattern("MMM dd yyyy h a")) + " to: " + this.endTime
                .format(DateTimeFormatter.ofPattern("MMM dd yyyy h a")) + ")";
    }

    @Override
    public String saveFormat() {
        return super.saveFormat() + "|E" + "|" + this.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"))
                + "|" + this.endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
    }

    @Override
    public boolean compareDate(LocalDate date) {
        return (date.isEqual(this.startTime.toLocalDate()) || date.isAfter(this.startTime.toLocalDate()))
                && (date.isEqual(this.endTime.toLocalDate()) || date.isBefore(this.endTime.toLocalDate()));
    }
}
