package Manager.TaskManager;

import Manager.History.InMemoryHistoryManager;
import Tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, SingleTask> taskList;
    private HashMap<Integer, Epic> epicList;
    private HashMap<Integer, Subtask> subtaskList;
    private InMemoryHistoryManager history;
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTask = new ArrayList<>();
        for (SingleTask task : taskList.values()) {
            allTask.add(task);
        }
        for (Epic epic : epicList.values()) {
            allTask.add(epic);
            if (!epic.getSubtasks().isEmpty()) {
                for (Subtask subtasks : epic.getSubtasks().values()) {
                    allTask.add(subtasks);
                }
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
            history.addTaskInHistory(taskList.get(id));
            return taskList.get(id);
        }
        if (epicList.containsKey(id)) {
            history.addTaskInHistory(epicList.get(id));
            return epicList.get(id);
        }
        if (subtaskList.containsKey(id)) {
            history.addTaskInHistory(subtaskList.get(id));
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
        newSubtask.setStatus(TaskStatus.NEW);
        subtaskList.put(generateId(), newSubtask);
        epicList.get(newSubtask.getIdEpic()).addSubtask(newSubtask);
        epicList.get(newSubtask.getIdEpic()).setStatus();
    }

    @Override
    public boolean removeTask(int id) {
        if (taskList.containsKey(id)) {
            taskList.remove(id);
            history.remove(id);
            return true;
        }
        if (epicList.containsKey(id)) {
            for (int subtaskId : epicList.get(id).getSubtasks().keySet()) {
                subtaskList.remove(subtaskId);
                history.remove(subtaskId);
            }
            epicList.remove(id);
            history.remove(id);
            return true;
        }
        if (subtaskList.containsKey(id)) {
            epicList.get(subtaskList.get(id).getIdEpic()).removeSubtask(subtaskList.get(id));
            epicList.get(subtaskList.get(id).getIdEpic()).setStatus();
            subtaskList.remove(id);
            history.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void updateSingleTask(SingleTask updatedSingleTask) {
        taskList.put(updatedSingleTask.getId(), updatedSingleTask);
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        Epic newEpic = new Epic(updatedEpic.getTitle(), updatedEpic.getDescription(), updatedEpic.getId());
        epicList.put(newEpic.getId(), newEpic);
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