package Tasks;

public class Subtask extends Task{
    private int idEpic;

    private TaskStatus status;

    public Subtask(String title, String description, int id, int idEpic) {
        super(title, description, id);
        this.idEpic = idEpic;
        status=TaskStatus.NEW;
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
        return "Subtask" +
                " id = " + id+
                ", title= '" + super.title + "'" +
                ", status= '" + status + "'";
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
