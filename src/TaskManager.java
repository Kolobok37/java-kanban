import java.sql.SQLOutput;
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

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> allTask = new ArrayList<>();
        for (Task task : taskList.values()) {
            allTask.add(task);
        }
        for (Epic epic : epicList.values()) {
            allTask.add(epic);
            if (!epic.getSubtasks().isEmpty()) {
                for (int subtaskId : epic.getSubtasks()) {
                    allTask.add(subtaskList.get(subtaskId));
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
        id++;
    }
    public void setEpic(Epic newEpic){
                epicList.put(id, newEpic);
                id++;
        }

    public void setSubtask(Task newTask, int epicId) {
        Subtask newSubtask = new Subtask(newTask.getTitle(), newTask.getDescription(), id, epicId);
        subtaskList.put(id, newSubtask);
        epicList.get(epicId).addSubtask(id);
        id++;
        if(epicList.get(epicId).getStatus().equals("DONE")) {
            epicList.get(epicId).setStatus("IN_PROGRESS");
        }
    }

    public boolean removeTask(int id) {
        if (taskList.containsKey(id)) {
            taskList.remove(id);
            return true;
        }
        if (epicList.containsKey(id)) {
            epicList.remove(id);
            return true;
        }
        if (subtaskList.containsKey(id)) {
            int epicId = subtaskList.get(id).getIdEpic();
            subtaskList.remove(id);
            epicList.get(epicId).removeSubtask(id);
            return true;
        }
        return false;
    }

    public void updateTask(Task updatedTask) {
                taskList.put(updatedTask.getId(), updatedTask);
    }
    public void updateEpic(Epic updatedEpic){
        Epic newEpic = new Epic(updatedEpic.getTitle(), updatedEpic.getDescription(), updatedEpic.getId());
        newEpic.setStatus(updatedEpic.getStatus());
        epicList.put(newEpic.getId(), newEpic);
    }

    public void updateSubtask(Subtask updatedSubtask) {
        int epicId = updatedSubtask.getIdEpic();
        Subtask newSubtask = new Subtask(updatedSubtask.getTitle(), updatedSubtask.getDescription(), updatedSubtask.getId(),
                epicId);
        newSubtask.setStatus(updatedSubtask.getStatus());

        subtaskList.put(newSubtask.getId(), newSubtask);
        switch (newSubtask.getStatus()){
            case "NEW":
                if(epicList.get(epicId).getStatus().equals("NEW")){
                    break;
                }
                else{
                    epicList.get(epicId).setStatus("IN_PROGRESS");
                }
                break;
            case "DONE":
                if(epicList.get(epicId).getStatus().equals("DONE")){
                    break;
                }
                else{
                    for(int i = 0; i<epicList.get(epicId).getSubtasks().size(); i++){
                        if(subtaskList.get(epicList.get(epicId).getSubtasks().get(i)).getStatus().equals("DONE")){
                            epicList.get(epicId).setStatus("DONE");
                        }
                        else {
                            epicList.get(epicId).setStatus("IN_PROGRESS");
                            break;
                        }
                    }
                }
                break;
            case "IN_PROGRESS":
                epicList.get(epicId).setStatus("IN_PROGRESS");
        }
        }

    public ArrayList<Subtask> getSubtaskOfEpic(int idEpic) {
        ArrayList<Subtask> listSubtask = new ArrayList<>();
        for(Integer i:epicList.get(idEpic).getSubtasks()){
            listSubtask.add(subtaskList.get(i));
        }
        return listSubtask;
    }
}