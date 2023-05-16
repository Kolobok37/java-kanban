package Tasks;


import java.time.Duration;
import java.time.LocalDateTime;

public class SingleTask extends Task{
    public SingleTask(String title, String description, int id, LocalDateTime startTime, Duration duration) {
        super(title,description,id);
        this.startTime=startTime;
        this.duration= duration;
        status = TaskStatus.NEW;
        type = TaskType.SingleTask;
    }

    @Override
    public String toString() {
        return id+",SingleTask,"+title +","+status+","+description+","+startTime+","+duration;

    }
    @Override
    public String getTitle() {
        return title;
    }
    @Override

    public void setTitle(String title) {
        this.title = title;
    }
    @Override

    public String getDescription() {
        return description;
    }
@Override
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public int getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
