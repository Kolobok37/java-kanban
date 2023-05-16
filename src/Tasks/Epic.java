package Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;


public class Epic extends Task {
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();
    LocalDateTime endTime;

    public Epic(String title, String description, int id,LocalDateTime startTime, Duration duration) {
        super(title, description, id);
        type = TaskType.Epic;
        status = TaskStatus.NEW;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Epic(String title, String description, int id) {
        super(title, description, id);
        type = TaskType.Epic;
        status = TaskStatus.NEW;
        duration = Duration.ZERO;
        startTime = LocalDateTime.MIN;
    }

    @Override
    public String toString() {
        return id + ",Epic," + title + "," + status + "," + description+","+startTime+","+duration;
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
        calculateTime();
    }

    public void addSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
        calculateTime();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void loadStatus(TaskStatus status) {
        this.status = status;
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

    public void calculateTime() {
        startTime = LocalDateTime.MAX;
        endTime = LocalDateTime.MIN;
        duration = Duration.ZERO;
        for (Subtask subtask : subtasks.values()) {
            if(subtask.getStartTime()==null){
                continue;
            }
                if (subtask.getStartTime().isBefore(startTime)) {
                    startTime = subtask.getStartTime();
                }
                if (subtask.getEndTime().isAfter(endTime)) {
                    endTime = subtask.getEndTime();
                }
                duration = duration.plus(subtask.getDuration());
            }
        if (startTime==LocalDateTime.MAX){
            startTime = null;
            endTime = null;
            duration = null;
        }
        }
    }

