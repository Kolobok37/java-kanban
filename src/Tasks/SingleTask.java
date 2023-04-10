package Tasks;

public class SingleTask extends Task{
    public SingleTask(String title, String description, int id) {
        super(title,description,id);
        status = TaskStatus.NEW;
        type = TaskType.SingleTask;
    }

    @Override
    public String toString() {
        return id+",SingleTask,"+title +","+status+","+description;

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
