package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    public Task(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;

    }

    protected String title;

    protected String description;
    protected int id;
    protected TaskStatus status;
    public LocalDateTime startTime;
    public Duration duration;
    public  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
    protected TaskType type;

    public TaskType getType() {
        return type;
    }

    abstract public String getTitle();

    abstract public void setTitle(String title);

    abstract public String getDescription();

    abstract public void setDescription(String description);

    public int getId(){
        return id;
    }
    public LocalDateTime getEndTime(){
        return startTime.plus(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }
}
