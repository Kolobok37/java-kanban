package Manager.TaskManager;

import Manager.History.InMemoryHistoryManager;
import Tasks.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public int generateId() {
        return id++;
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
    public void setSingleTask(SingleTask newSingleTask){
        try {
            checkTimeAtAdding(newSingleTask);
            taskList.put(newSingleTask.getId(), newSingleTask);
        } catch(RuntimeException e) {
            System.out.println("Обновлённая подзадача не правильно задана или её нет в списке задач!");
        }
    }

    @Override
    public void setEpic(Epic newEpic){
        epicList.put(newEpic.getId(), newEpic);
    }

    @Override
    public void setSubtask(Subtask newSubtask){
        try {
            checkTimeAtAdding(newSubtask);
            subtaskList.put(newSubtask.getId(), newSubtask);
            epicList.get(newSubtask.getIdEpic()).addSubtask(newSubtask);
            epicList.get(newSubtask.getIdEpic()).setStatus();
        } catch (RuntimeException e) {
            System.out.println("Обновлённая подзадача не правильно задана или её нет в списке задач!");
        }
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
        try {
            getTask(updatedSingleTask.getId()).getId();
            taskList.put(updatedSingleTask.getId(), updatedSingleTask);
        } catch (NullPointerException e) {
            System.out.println("Обновлённая задача не задана или её нет в списке задач!");
        }
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        try {
            getTask(updatedEpic.getId()).getId();
            epicList.put(updatedEpic.getId(), updatedEpic);
        } catch (NullPointerException e) {
            System.out.println("Обновлёный эпик не задан или его нет в списке задач!");
        }
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        try {
            getTask(updatedSubtask.getId()).getId();
            subtaskList.put(updatedSubtask.getId(), updatedSubtask);
            epicList.get(updatedSubtask.getIdEpic()).addSubtask(updatedSubtask);
            epicList.get(updatedSubtask.getIdEpic()).setStatus();
        } catch (NullPointerException e) {
            System.out.println("Обновлённая подзадача не задан или её нет в списке задач!");
        }
    }

    @Override
    public TreeSet getPrioritizedTasks() {
        ArrayList<Task> tasks = getAllTasks();
        TreeSet<Task> tasksSet = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task s1, Task s2) {
                if (s2.getStartTime() == null || s1.getStartTime() == null) {
                    if (s2.getStartTime() == null) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    return s1.getStartTime().compareTo(s2.getStartTime());
                }
            }
        });
        Set<Task> set = tasks.stream().filter((task) -> task.getType() == TaskType.SingleTask ||
                task.getType() == TaskType.Subtask).collect(Collectors.toSet());
        tasksSet.addAll(set);
        return tasksSet;
    }

    public void checkTimeAtAdding(Task taskAdding) throws RuntimeException {
        if(taskAdding.getStartTime()==null){
            return;
        }
        for (Task task : getAllTasks()) {
            if(task.getStartTime()==null){
                continue;
            }
            if (!(taskAdding.getStartTime().isBefore(task.getStartTime())&&taskAdding.getStartTime().plus(taskAdding.getDuration()).
                    isBefore(task.getStartTime())||
                    taskAdding.getStartTime().isAfter(task.getStartTime().plus(task.getDuration())))) {
                throw new RuntimeException("Ошибка задачи времени");
            }
        }
    }
    static public TaskStatus getTaskStatusFromString(String status){
        switch (status){
            case "IN_PROGRESS":
                return TaskStatus.IN_PROGRESS;
            case "DONE":
                return TaskStatus.DONE;
            default:
                return TaskStatus.NEW;
        }
    }
}