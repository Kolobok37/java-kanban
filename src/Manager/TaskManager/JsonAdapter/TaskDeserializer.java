package Manager.TaskManager.JsonAdapter;

import Tasks.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Tasks.Task;

public class TaskDeserializer implements JsonDeserializer<ArrayList<Task>>{
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");

    @Override
    public ArrayList<Task> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        ArrayList<Task> allTask = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++)
            switch (jsonArray.get(i).getAsJsonObject().get("type").getAsString()) {
                case "SingleTask": {
                    Task newTask = new SingleTask(jsonArray.get(i).getAsJsonObject().get("title").getAsString(),
                            jsonArray.get(i).getAsJsonObject().get("description").getAsString(),
                            jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                            context.deserialize(jsonArray.get(i).getAsJsonObject().get("startTime"), LocalDateTime.class),
                            context.deserialize(jsonArray.get(i).getAsJsonObject().get("duration"), Duration.class));
                    allTask.add(newTask);
                    break;
                }
                case "Epic": {
                    Task newTask=new Epic(jsonArray.get(i).getAsJsonObject().get("title").getAsString(),
                            jsonArray.get(i).getAsJsonObject().get("description").getAsString(),
                            jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                            context.deserialize(jsonArray.get(i).getAsJsonObject().get("startTime"), LocalDateTime.class),
                            context.deserialize(jsonArray.get(i).getAsJsonObject().get("duration"), Duration.class));
                    allTask.add(newTask);
                    break;
                }
                case "Subtask": {
                    Task newTask = new Subtask(jsonArray.get(i).getAsJsonObject().get("title").getAsString(),
                            jsonArray.get(i).getAsJsonObject().get("description").getAsString(),
                            jsonArray.get(i).getAsJsonObject().get("id").getAsInt(),
                            jsonArray.get(i).getAsJsonObject().get("idEpic").getAsInt(),
                            context.deserialize(jsonArray.get(i).getAsJsonObject().get("startTime"), LocalDateTime.class),
                            context.deserialize(jsonArray.get(i).getAsJsonObject().get("duration"), Duration.class));
                    allTask.add(newTask);
                    break;
                }
            }
        return allTask;
    }
}
