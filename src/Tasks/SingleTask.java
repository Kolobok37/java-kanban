package Tasks;


import java.time.Duration;
import java.time.LocalDateTime;

public class SingleTask extends Task{
    protected Duration duration;


    public SingleTask(String title, String description, int id, LocalDateTime startTime, Duration duration) {
        super(title,description,id);
        this.startTime=startTime;
        this.duration= duration;
        status = TaskStatus.NEW;
        type = TaskType.SingleTask;
    }

    public SingleTask(String title, String description, int id, LocalDateTime startTime, Duration duration,String status) {
        super(title,description,id);
        this.startTime=startTime;
        this.duration= duration;
        this.status = searchStatus(status);
        type = TaskType.SingleTask;
    }

    @Override
    public String toString() {
        return id+",SingleTask,"+title +","+status+","+description+","+startTime.format(formatter)+","+duration;

    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
