package Manager.TaskManager.JsonAdapter;

import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Subtask;
import Tasks.Task;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskSerializer implements JsonSerializer<Task> {
    @Override
    public JsonObject serialize(Task task, Type typeOfTask, JsonSerializationContext context) {
        JsonObject jsonTask = new JsonObject();
        LocalDateTimeSerializer dataTimeSerializer = new LocalDateTimeSerializer();
        DurationSerialize durationSerializer = new DurationSerialize();
            switch (task.getType()) {
                case SingleTask: {
                    jsonTask.addProperty("id", task.getId());
                    jsonTask.addProperty("type", task.getType().toString());
                    jsonTask.addProperty("title", task.getTitle());
                    jsonTask.addProperty("status", task.getStatus().toString());
                    jsonTask.addProperty("description", task.getDescription());
                    jsonTask.add("startTime", dataTimeSerializer.serialize(task.getStartTime(), typeOfTask, context));
                    jsonTask.add("duration", durationSerializer.serialize(task.getDuration(), typeOfTask, context));
                    break;
                }
                case Epic: {
                    jsonTask.addProperty("id", task.getId());
                    jsonTask.addProperty("type", task.getType().toString());
                    jsonTask.addProperty("title", task.getTitle());
                    jsonTask.addProperty("status", task.getStatus().toString());
                    jsonTask.addProperty("description", task.getDescription());
                    Epic epic = (Epic) task;
                    jsonTask.addProperty("subtaskId", epic.subtasks.keySet().toString());
                    jsonTask.add("startTime", dataTimeSerializer.serialize(task.getStartTime(), typeOfTask, context));
                    jsonTask.add("duration", durationSerializer.serialize(task.getDuration(), typeOfTask, context));
                    break;
                }
                case Subtask: {
                    jsonTask.addProperty("id", task.getId());
                    jsonTask.addProperty("type", task.getType().toString());
                    jsonTask.addProperty("title", task.getTitle());
                    jsonTask.addProperty("status", task.getStatus().toString());
                    jsonTask.addProperty("description", task.getDescription());
                    Subtask subtask = (Subtask) task;
                    jsonTask.addProperty("idEpic", ((Subtask) task).getIdEpic());
                    jsonTask.add("startTime", dataTimeSerializer.serialize(task.getStartTime(), typeOfTask, context));
                    jsonTask.add("duration", durationSerializer.serialize(task.getDuration(), typeOfTask, context));
                    break;
                }
            }
        return jsonTask;
    }
}
