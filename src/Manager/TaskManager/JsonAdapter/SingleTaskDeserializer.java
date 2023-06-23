package Manager.TaskManager.JsonAdapter;

import Tasks.SingleTask;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;

public class SingleTaskDeserializer implements JsonDeserializer<SingleTask> {
    @Override
    public SingleTask deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        SingleTask task = null;
        if (jsonObject.getAsJsonObject().get("type").getAsString().equals("SingleTask")) {
            task = new SingleTask(jsonObject.getAsJsonObject().get("title").getAsString(),
                    jsonObject.getAsJsonObject().get("description").getAsString(),
                    jsonObject.getAsJsonObject().get("id").getAsInt(),
                    context.deserialize(jsonObject.getAsJsonObject().get("startTime"), LocalDateTime.class),
                    context.deserialize(jsonObject.getAsJsonObject().get("duration"), Duration.class),
                    jsonObject.getAsJsonObject().get("status").getAsString());
        }
        return task;
    }
}
