package Manager.TaskManager;

import Manager.History.InMemoryHistoryManager;
import Tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, SingleTask> taskList;
    protected HashMap<Integer, Epic> epicList;
    protected HashMap<Integer, Subtask> subtaskList;
    protected InMemoryHistoryManager history;
    private int id;

    public InMemoryTaskManager(InMemoryHistoryManager history) {
        taskList = new HashMap<>();
        epicList = new HashMap<>();
        subtaskList = new HashMap<>();
        this.history = history;
        id = 1;
    }

    public LinkedList<Task> getHistoryMemory() {
        return history.getHistory();
    }

     public int getId() {
        return id;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTask = new ArrayList<>();
        allTask.addAll(taskList.values());
        for (Epic epic : epicList.values()) {
            allTask.add(epic);
            if (!epic.getSubtasks().isEmpty()) {
                allTask.addAll(epic.getSubtasks().values());
            }
        }
        return allTask;
    }

    @Override
    public void clearAll() {
        taskList.clear();
        epicList.clear();
        subtaskList.clear();
        history.clear();

    }

    @Override
    public Task getTask(int id) {
        if (taskList.containsKey(id)) {
            history.add(taskList.get(id));
            return taskList.get(id);
        }
        if (epicList.containsKey(id)) {
            history.add(epicList.get(id));
            return epicList.get(id);
        }
        if (subtaskList.containsKey(id)) {
            history.add(subtaskList.get(id));
            return subtaskList.get(id);
        }
        return null;
    }

    @Override
    public void setSingleTask(SingleTask newSingleTask) {
        taskList.put(id, newSingleTask);
        generateId();
    }

    @Override
    public void setEpic(Epic newEpic) {
        epicList.put(id, newEpic);
        generateId();
    }

    @Override
    public void setSubtask(Subtask newSubtask) {
        subtaskList.put(id, newSubtask);
        epicList.get(newSubtask.getIdEpic()).addSubtask(newSubtask);
        epicList.get(newSubtask.getIdEpic()).setStatus();
        generateId();
    }

    @Override
    public void removeTask(int id) {
        if (taskList.containsKey(id)) {
            taskList.remove(id);
            history.remove(id);
        }
        if (epicList.containsKey(id)) {
            for (int subtaskId : epicList.get(id).getSubtasks().keySet()) {
                subtaskList.remove(subtaskId);
                history.remove(subtaskId);
            }
            epicList.remove(id);
            history.remove(id);
        }
        if (subtaskList.containsKey(id)) {
            epicList.get(subtaskList.get(id).getIdEpic()).removeSubtask(subtaskList.get(id));
            epicList.get(subtaskList.get(id).getIdEpic()).setStatus();
            subtaskList.remove(id);
            history.remove(id);
        }
    }

    @Override
    public void updateSingleTask(SingleTask updatedSingleTask) {
        taskList.put(updatedSingleTask.getId(), updatedSingleTask);
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        epicList.put(updatedEpic.getId(), updatedEpic);
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        subtaskList.put(updatedSubtask.getId(), updatedSubtask);
        epicList.get(updatedSubtask.getIdEpic()).addSubtask(updatedSubtask);
        epicList.get(updatedSubtask.getIdEpic()).setStatus();
    }

    private int generateId() {
        return id++;
    }

}