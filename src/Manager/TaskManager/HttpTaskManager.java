package Manager.TaskManager;

import Manager.Manager;
import Manager.TaskManager.JsonAdapter.*;
import Manager.server.KVTaskClient;
import Tasks.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HttpTaskManager extends InMemoryTaskManager {
    Gson gson;
    private String url;
    private KVTaskClient client;

    public HttpTaskManager(String url) {// "http://localhost:8078/"
        super(Manager.getDefaultHistory());
        this.url = url;
        client = new KVTaskClient(url);
        gson = new GsonBuilder().registerTypeAdapter(SingleTask.class, new TaskSerializer()).
                registerTypeAdapter(Epic.class, new TaskSerializer()).
                registerTypeAdapter(Subtask.class, new TaskSerializer()).
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).
                registerTypeAdapter(Duration.class, new DurationSerialize()).
                registerTypeAdapter(SingleTask.class, new SingleTaskDeserializer()).
                registerTypeAdapter(Epic.class, new EpicDeserializer()).
                registerTypeAdapter(Subtask.class, new SubtaskDeserializer()).
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).
                registerTypeAdapter(Duration.class, new DurationDeserializer()).
                create();
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save("tasks/history", gson.toJson(history.getHistoryId()));
        return task;
    }

    @Override
    public void setEpic(Epic newEpic) {
        super.setEpic(newEpic);
        save("tasks/epic", gson.toJson(epicList.values()));
        save("tasks",gson.toJson(getAllTasks()));
    }


    @Override
    public void setSingleTask(SingleTask newSingleTask) {
        super.setSingleTask(newSingleTask);
        save("tasks/task", gson.toJson(taskList.values()));
        save("tasks",gson.toJson(getAllTasks()));
    }

    @Override
    public void setSubtask(Subtask newSubtask) {
        super.setSubtask(newSubtask);
        save("tasks/subtask", gson.toJson(subtaskList.values()));
        save("tasks/epic", gson.toJson(epicList.values()));
        save("tasks",gson.toJson(getAllTasks()));
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save("tasks",gson.toJson(getAllTasks()));
    }

    private void save(String key, String value) { // делавет сохранение через Client
        try {
            client.put(key, value);
        } catch (IOException | InterruptedException e) {

        }
    }


    public TaskData loadFromJson(String key) {
        try {
            System.out.println(key);
            String jsonTask = client.load(key);
            System.out.println(jsonTask);
            ArrayList<Task> task = new ArrayList<>();
            task.addAll(gson.fromJson(jsonTask,new TypeToken<ArrayList<SingleTask>>() {
            }.getType()));
            task.addAll(gson.fromJson(jsonTask,new TypeToken<ArrayList<Epic>>() {
            }.getType()));
            task.addAll(gson.fromJson(jsonTask,new TypeToken<ArrayList<Subtask>>() {
            }.getType()));

            for (int i =0;i<task.size();i++){
                if(Objects.isNull(task.get(i))){
                    task.remove(i);
                    i=-1;
                }
            }

            String jsonHistory = client.load("tasks/history");
            List<Integer> history = gson.fromJson(jsonHistory, new TypeToken<List<Integer>>() {
            }.getType());
            System.out.println(history);
            TaskData tasks = new TaskData(task,history);
            return tasks;
        } catch (IOException | InterruptedException exception) {
            return null;
        }
    }

    public static void main(String[] args) {
        HttpTaskManager manager = new HttpTaskManager("http://localhost:8078/");
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
        manager.removeTask(1);
        TaskData tasks = manager.loadFromJson("tasks");
        System.out.println(tasks.getTasks());
        System.out.println(tasks.getHistory());

    }
}
