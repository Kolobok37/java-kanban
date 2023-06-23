package Manager.TaskManager.JsonAdapter;

import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Task;
import Tasks.TaskData;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DurationDeserializer implements JsonDeserializer<Duration> {
    @Override
    public Duration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Duration duration = Duration.ofSeconds(jsonObject.get("seconds").getAsInt());
        return duration;
    }
}

