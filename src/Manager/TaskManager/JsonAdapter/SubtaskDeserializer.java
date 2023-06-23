package Manager.TaskManager.JsonAdapter;

import Tasks.Subtask;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class SubtaskDeserializer implements JsonDeserializer<Subtask> {

    @Override
    public Subtask deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Subtask subtask = null;
        if (jsonObject.getAsJsonObject().get("type").getAsString().equals("Subtask")) {
            subtask = new Subtask(jsonObject.getAsJsonObject().get("title").getAsString(),
                    jsonObject.getAsJsonObject().get("description").getAsString(),
                    jsonObject.getAsJsonObject().get("id").getAsInt(),
                    jsonObject.getAsJsonObject().get("idEpic").getAsInt(),
                    context.deserialize(jsonObject.getAsJsonObject().get("startTime"), LocalDateTime.class),
                    context.deserialize(jsonObject.getAsJsonObject().get("duration"), Duration.class),
                    jsonObject.getAsJsonObject().get("status").getAsString());
        }
        return subtask;
    }
}