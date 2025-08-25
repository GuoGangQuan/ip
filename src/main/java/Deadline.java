public class Deadline extends Task {
    protected String by;

    public Deadline(String[] detail) {
        super(detail[0]);
        this.by = detail[1].trim();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String saveFormat() {
        return super.saveFormat() + "|D"+"|"+this.by;
    }
}
