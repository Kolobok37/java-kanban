package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected String title;
    protected String description;
    protected int id;
    protected TaskStatus status;
    protected LocalDateTime startTime;
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
    protected TaskType type;

    public Task(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    abstract public Duration getDuration();
    public TaskType getType() {
        return type;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getId(){
        return id;
    }

    public LocalDateTime getEndTime(){
        return startTime.plus(getDuration());
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus searchStatus(String status){
        TaskStatus statusEnum = TaskStatus.NEW;
        for (TaskStatus statusTask : TaskStatus.values()) {
            if (status.equals(statusTask.toString())) {
                statusEnum = statusTask;
            }
        }
        return statusEnum;
    }
}
