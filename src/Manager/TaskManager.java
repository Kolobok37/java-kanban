package Manager;

import Tasks.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    HashMap<Integer, Task> taskList = new HashMap<>();
    HashMap<Integer, Epic> epicList = new HashMap<>();
    HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    int id = 1;

    public int getId() {
        return id;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTask = new ArrayList<>();
        for (Task task : taskList.values()) {
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

    public void clearAll() {
        taskList.clear();
        epicList.clear();
        subtaskList.clear();
    }

    public Task getTask(int id) {
        if (taskList.containsKey(id)) {
            taskList.get(id).toString();
            return taskList.get(id);
        }
        if (epicList.containsKey(id)) {
            epicList.get(id).toString();
            return epicList.get(id);
        }
        if (subtaskList.containsKey(id)) {
            subtaskList.get(id).toString();
            return subtaskList.get(id);
        }
        return null;
    }

    public void setTask(Task newTask) {
        taskList.put(id, newTask);
        generateId();
    }

    public void setEpic(Epic newEpic) {
        epicList.put(id, newEpic);
        generateId();
    }

    public void setSubtask(Subtask newSubtask) {
        newSubtask.setStatus("NEW");
        subtaskList.put(generateId(), newSubtask);
        epicList.get(newSubtask.getIdEpic()).addSubtask(newSubtask);
    }

    public boolean removeTask(int id) {
        if (taskList.containsKey(id)) {
            taskList.remove(id);
            return true;
        }
        if (epicList.containsKey(id)) {
            for(int subtaskId: epicList.get(id).getSubtasks().keySet()){
                subtaskList.remove(subtaskId);
            }
            epicList.remove(id);
            return true;
        }
        if (subtaskList.containsKey(id)) {
            subtaskList.remove(id);
            epicList.get(subtaskList.get(id).getIdEpic()).removeSubtask(subtaskList.get(id));
            return true;
        }
        return false;
    }

    public void updateTask(Task updatedTask) {
        taskList.put(updatedTask.getId(), updatedTask);
    }

    public void updateEpic(Epic updatedEpic) {
        Epic newEpic = new Epic(updatedEpic.getTitle(), updatedEpic.getDescription(), updatedEpic.getId());
        newEpic.setStatus(updatedEpic.getStatus());
        epicList.put(newEpic.getId(), newEpic);
    }

    public void updateSubtask(Subtask updatedSubtask) {
        subtaskList.put(updatedSubtask.getId(), updatedSubtask);
        epicList.get(updatedSubtask.getIdEpic()).addSubtask(updatedSubtask);
    }

    private int generateId() {
        return id++;
    }
}