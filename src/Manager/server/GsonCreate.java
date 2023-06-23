package Manager.server;

import Manager.TaskManager.JsonAdapter.*;
import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Subtask;
import Tasks.TaskData;
import com.google.gson.Gson;

import java.time.Duration;
import java.time.LocalDateTime;

import com.google.gson.*;

public class GsonCreate {
    public static Gson createGson(){
    Gson gson = new GsonBuilder().registerTypeAdapter(SingleTask .class, new TaskSerializer()).
    registerTypeAdapter(Epic .class, new TaskSerializer()).
    registerTypeAdapter(Subtask .class, new TaskSerializer()).
    registerTypeAdapter(LocalDateTime .class, new LocalDateTimeSerializer()).
    registerTypeAdapter(Duration .class, new DurationSerialize()).
    registerTypeAdapter(SingleTask.class, new SingleTaskDeserializer()).
    registerTypeAdapter(Epic.class, new EpicDeserializer()).
    registerTypeAdapter(Subtask.class, new SubtaskDeserializer()).
    registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).
    registerTypeAdapter(Duration.class, new DurationDeserializer()).
    create();
    return gson;
    }

}
