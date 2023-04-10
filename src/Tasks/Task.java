package Tasks;

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
    protected TaskType type;

    public TaskType getType() {
        return type;
    }

    abstract public String getTitle();

    abstract public void setTitle(String title);

    abstract public String getDescription();

    abstract public void setDescription(String description);

    abstract public int getId();

}
