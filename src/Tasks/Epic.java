package Tasks;

import java.util.HashMap;


public class Epic extends Task{
    TaskStatus status;
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Epic(String title, String description, int id) {
        super(title, description, id);
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
        return "Epic " +
                "id = " + super.id +
                ": title= '" + super.title + "'" +
                ", status= '" + status + '\'' +
                ", subtask: " + subtasks.size();
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


    /*public void setStatus(String statusSubtask) {
        switch (statusSubtask) {
            case "NEW":
                if (!status.equals("NEW")) {
                    status = "IN_PROGRESS";
                } else {
                    status = "NEW";
                }
                break;
            case "DONE":    // При значении субтаска DONE, если епик не завершён, то проверяем по условиям, если завершён то ничего не делаем.
                if (!status.equals("DONE")) {
                    for (Subtask subtask : subtasks.values()) {
                        if (subtask.getStatus().equals("DONE")) {
                            status = "DONE";
                        } else {
                            status = "IN_PROGRESS";
                            break;
                        }
                    }

                }
                break;
            case "IN_PROGRESS":
                status = "IN_PROGRESS";
                break;
        }
    }
*/
    public TaskStatus getStatus() {
        return status;
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
        }
        else {
            status = TaskStatus.IN_PROGRESS;
        }
    }
}
