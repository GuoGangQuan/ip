public class Deadline extends Task {
    protected String by;

    public Deadline(String[] detail) {
        super(detail[0]);
        this.by = detail[1];
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
