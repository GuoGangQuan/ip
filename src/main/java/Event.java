public class Event extends Task {
    protected String startTime;
    protected String endTime;

    public Event(String[] detail) {
        super(detail[0]);
        this.startTime = detail[1];
        this.endTime = detail[2];
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }

    @Override
    public String saveFormat() {
        return super.saveFormat()+"|E"+"|"+this.startTime+"|"+this.endTime;
    }
}
