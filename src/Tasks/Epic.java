package Tasks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;


public class Epic extends Task {
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();
    public List<Integer> subtaskId;
    Duration duration;

    private LocalDateTime endTime;

    public Epic(String title, String description, int id, LocalDateTime startTime, Duration duration) {
        super(title, description, id);
        type = TaskType.Epic;
        status = TaskStatus.NEW;
        this.startTime = startTime;
    }

    public Epic(String title, String description, int id, LocalDateTime startTime, Duration duration, List<Integer>  subtaskId, String status) {
        super(title, description, id);
        type = TaskType.Epic;
        this.duration=duration;
        this.startTime = startTime;
        this.subtaskId = subtaskId;
        this.status=searchStatus(status);
     }

    public Epic(String title, String description, int id) {
        super(title, description, id);
        type = TaskType.Epic;
        status = TaskStatus.NEW;
        startTime = LocalDateTime.MIN;
    }

    @Override
    public String toString() {
        return id + ",Epic," + title + "," + status + "," + description + "," + startTime + "," + duration+"," + getSubtaskId();
    }


    public void removeSubtask(Subtask deletedSubtask) {
        subtaskId.remove((Integer)deletedSubtask.getId());
        subtasks.remove(subtasks.get(deletedSubtask.getId()));
        calculateTime();
    }

    public void addSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
        calculateTime();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
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

    @Override
    public Duration getDuration() {
        Duration duration = Duration.ZERO;
        for (Subtask subtask : subtasks.values()) {
            duration = duration.plus(subtask.getDuration());
        }
        return duration;
    }

    private void calculateTime() {
        startTime = LocalDateTime.MAX;
        endTime = LocalDateTime.MIN;
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getStartTime() == null) {
                continue;
            }
            if (subtask.getStartTime().isBefore(startTime)) {
                startTime = subtask.getStartTime();
            }
            if (subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }
        }
        if (startTime == LocalDateTime.MAX) {
            startTime = null;
            endTime = null;
        }
        duration=getDuration();
    }

    public List<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void addSubtaskId(int id){
        subtaskId.add(id);
    }

}

