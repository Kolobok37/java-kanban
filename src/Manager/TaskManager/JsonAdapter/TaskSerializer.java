package Manager.TaskManager.JsonAdapter;

import Tasks.Task;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class TaskSerializer implements JsonSerializer<Task> {
    @Override
    public JsonElement serialize(Task task, Type typeOfTask, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("id", task.getId());
        result.addProperty("type", "SingleTask");
        result.addProperty("title", task.getTitle());
        result.addProperty("status", task.getStatus().toString());
        result.addProperty("description", task.getDescription());
        result.add("startTime", context.serialize(task.getStartTime()));
        result.addProperty("duration", task.getDuration().toString());
        return result;
    }
}
