package Manager.TaskManager.JsonAdapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;

public class DurationSerialize implements JsonSerializer<Duration>  {
    @Override
    public JsonObject serialize(Duration duration, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("seconds", duration.getSeconds());
        return result;
    }
}
