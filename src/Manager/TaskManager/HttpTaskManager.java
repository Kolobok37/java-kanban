package Manager.TaskManager;

import Manager.Manager;
import Manager.server.GsonCreate;
import Manager.server.HttpTaskServer;
import Manager.server.KVServer;
import Manager.server.KVTaskClient;
import Tasks.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class HttpTaskManager extends FileBackedTasksManager {
    Gson gson;
    private String url;
    private KVTaskClient client;

    public HttpTaskManager(String url, String name) throws IOException {// "http://localhost:8078/"
        super(Manager.getDefaultHistory(), new File(name));
        this.url = url;
        client = new KVTaskClient(url);
        gson = GsonCreate.createGson();
    }

    public static void main(String[] args) throws IOException {
        KVServer kvServer = new KVServer();
        HttpTaskManager manager = new HttpTaskManager("localhost", "manager");
        kvServer.start();
        manager.client.register();
        SingleTask testSingleTask1 = new SingleTask("Мыть пол", "Помыть до 21:00", manager.generateId(),
                LocalDateTime.of(2000, 1, 1, 7, 0), Duration.ofHours(1));
        SingleTask testSingleTask2 = new SingleTask("Задача2", "Помыть до 21:00", manager.generateId(),
                LocalDateTime.of(2000, 1, 1, 9, 0), Duration.ofHours(1));
        Epic testEpic1 = new Epic("Сделать ремонт", "В квартире", manager.generateId());
        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                manager.generateId(), testEpic1.getId(), LocalDateTime.of(2000, 1, 1, 3, 0),
                Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        manager.setSingleTask(testSingleTask2);
        manager.setEpic(testEpic1);
        manager.setSubtask(testSubtask1);
        manager.getTask(2);
        manager.getTask(1);
        HttpTaskManager manager2 = manager.loadFromJson("manager", "manager2");
        System.out.println(manager2.getAllTasks());
        System.out.println(manager2.getHistoryMemory());
    }


    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save(file.getName() + "/history", getHistoryMemory().stream().map(task1 -> task1.getId()).collect(Collectors.toList()).toString());
        return task;
    }

    @Override
    public void setEpic(Epic newEpic) {
        super.setEpic(newEpic);
        save(file.getName() + "/task", gson.toJson(getManagerToTaskData().getTasks()));

    }


    @Override
    public void setSingleTask(SingleTask newSingleTask) {
        super.setSingleTask(newSingleTask);
        save(file.getName() + "/task", gson.toJson(getManagerToTaskData().getTasks()));
    }

    @Override
    public void setSubtask(Subtask newSubtask) {
        super.setSubtask(newSubtask);
        save(file.getName() + "/task", gson.toJson(getManagerToTaskData().getTasks()));
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save(file.getName() + "/task", gson.toJson(getManagerToTaskData().getTasks()));
    }

    private TaskData getManagerToTaskData() {
        TaskData data = new TaskData(getAllTasks(), getHistoryMemory().stream().
                map(task -> task.getId()).collect(Collectors.toList()));
        return data;
    }

    private void save(String key, String value) {
        try {
            client.put(key, value);
        } catch (IOException | InterruptedException e) {

        }
    }


    public HttpTaskManager loadFromJson(String key, String managerName) {
        try {
            HttpTaskManager manager = new HttpTaskManager("localhost", managerName);
            manager.client.register();
            String jsonTask = client.load(key + "/task");
            ArrayList<Task> task = new ArrayList<>();
            task.addAll(gson.fromJson(jsonTask, new TypeToken<ArrayList<SingleTask>>() {
            }.getType()));
            task.addAll(gson.fromJson(jsonTask, new TypeToken<ArrayList<Epic>>() {
            }.getType()));
            task.addAll(gson.fromJson(jsonTask, new TypeToken<ArrayList<Subtask>>() {
            }.getType()));
            for (int i = 0; i < task.size(); i++) {
                if (Objects.isNull(task.get(i))) {
                    task.remove(i);
                    i = -1;
                }
            }
            String jsonHistory = client.load(key + "/history");
            List<Integer> history = gson.fromJson(jsonHistory, new TypeToken<List<Integer>>() {
            }.getType());
            for (Task t : task) {
                manager.setTask(t);
            }
            for (int taskId : history) {
                manager.getTask(taskId);
            }
            return manager;
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return null;
        }
    }
}
