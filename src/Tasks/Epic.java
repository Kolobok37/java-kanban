package Tasks;

import java.util.HashMap;


public class Epic extends Task {
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Epic(String title, String description, int id) {
        super(title, description, id);
        type = TaskType.Epic;
    }

    @Override
    public String toString() {
        return id+",Epic,"+title +","+status+","+description;
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

    public void removeSubtask(Subtask newSubtask) {
        subtasks.remove(subtasks.get(newSubtask.getId()));
    }

    public void addSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public TaskStatus getStatus() {
        return status;
    }
public void loadStatus(TaskStatus status){
        this.status=status;
    }
    public void setStatus() {
        int newTracker = 0;
        int doneTracker = 0;
        for (Subtask task : subtasks.values()) {
            switch (task.getStatus()) {
                case NEW:
                    newTracker++;
                    break;
                case DONE:
                    doneTracker++;
                    break;
                case IN_PROGRESS:
                    status = TaskStatus.IN_PROGRESS;
                    return;
            }
        }
        if (newTracker == subtasks.size()) {
            status = TaskStatus.NEW;
        } else if (doneTracker == subtasks.size()) {
            status = TaskStatus.DONE;
        } else {
            status = TaskStatus.IN_PROGRESS;
        }
    }
}
