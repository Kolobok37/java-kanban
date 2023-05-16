package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task{
    private int idEpic;

    public Subtask(String title, String description, int id, int idEpic,LocalDateTime startTime, Duration duration) {
        super(title, description, id);
        this.startTime=startTime;
        this.duration= duration;
        this.idEpic = idEpic;
        status=TaskStatus.NEW;
        type = TaskType.Subtask;
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
    @Override
    public String toString() {
        return id+",Subtask,"+title +","+status+","+description+","+idEpic+","+startTime+","+duration;
    }

    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }
}
