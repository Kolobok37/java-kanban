package Manager.TaskManager;

import Manager.History.InMemoryHistoryManager;
import Manager.Manager;
import Manager.TaskManager.JsonAdapter.*;
import Manager.server.KVTaskClient;
import Tasks.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class HttpTaskManager extends FileBackedTasksManager {
    Gson gson;
    private String url;
    private KVTaskClient client;

    public HttpTaskManager(InMemoryHistoryManager history, String url) {// "http://localhost:8078/"
        super(history, new File("TaskManager"));
        this.url = url;
        client = new KVTaskClient(url);
        gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskSerializer()).
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer()).
                registerTypeAdapter(TaskData.class, new TaskDeserializer()).
                registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).
                registerTypeAdapter(Duration.class, new DurationDeserializer()).
                create(); //
    }

//    @Override
//    public Task getTask(int id) {
//        Task task = super.getTask(id);
//        save();
//        return task;
//    }

    @Override
    public void setEpic(Epic newEpic) {    //Id Task присваевается в конструкторе и передаётся в метод уже с id,
        super.setEpic(newEpic);            // на данный момент логика такая.
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

//    @Override
//    public void removeTask(int id) {
//        super.removeTask(id);
//        save();
//    }

    private void save(String key, String value) { // делавет сохранение через Client
        try {
            client.save(key, value);
        } catch (IOException | InterruptedException e) {

        }
    }

//    public void save(TaskData taskData) throws IOException, InterruptedException {
//        String jsonTask = gson.toJson(taskData.getTasks());
//        client.save("tasks", jsonTask);
//        String jsonHistory = gson.toJson(taskData.getHistory());
//        client.save("history", jsonHistory);
//    }


    public TaskData loadFromJson(String key) {
        try {
            String jsonTask = client.load(key);
            ArrayList<Task> task = gson.fromJson(jsonTask, new TypeToken<ArrayList<Task>>() {
            }.getType());

            String jsonHistory = client.load("history");
            List<Integer> history = gson.fromJson(jsonHistory, new TypeToken<Integer>() {
            }.getType());
            System.out.println(history);
            TaskData tasks = new TaskData(task,history);
            return tasks;
        } catch (IOException | InterruptedException exception) {
            return null;
        }
    }

    public static void main(String[] args) {
        HttpTaskManager manager = new HttpTaskManager(Manager.getDefaultHistory(), "http://localhost:8078/");
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
manager.getTask(1);
        TaskData tasks = manager.loadFromJson("tasks");
        System.out.println(tasks.getTasks());
    }
}
