package Manager.TaskManager.JsonAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type typeOfTask, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("year", localDateTime.getYear());
        result.addProperty("month", localDateTime.getMonth().getValue());
        result.addProperty("day", localDateTime.getDayOfMonth());
        result.addProperty("hour", localDateTime.getHour());
        result.addProperty("minute", localDateTime.getMinute());
        return result;
    }
}
