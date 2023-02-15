package Tasks;

public class SingleTask extends Task{
    private TaskStatus status;    //Перечесление проодится в следующим спринте, не буду спешить

    public SingleTask(String title, String description, int id) {
        super(title,description,id);
        status = TaskStatus.NEW;
    }

    @Override
    public String toString() {
        return "Task " +
                "id=" + id +
                ": title= '" + title + '\'' +
                ", status= '" + status + '\'';
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
