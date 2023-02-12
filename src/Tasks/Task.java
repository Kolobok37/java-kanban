package Tasks;

public class Task {
    protected String title;
    protected String description;
    protected int id;
    protected String status;    //Перечесление проодится в следующим спринте, не буду спешить

    public Task(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.id = id;
        status = "NEW";
    }

    @Override
    public String toString() {
        return "Task " +
                "id=" + id +
                ": title= '" + title + '\'' +
                ", status= '" + status + '\'';
    }

    public String getTitle() {
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

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
