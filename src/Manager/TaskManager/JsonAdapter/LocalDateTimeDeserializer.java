package Manager.TaskManager.JsonAdapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        LocalDateTime localDateTime = LocalDateTime.of(jsonObject.get("year").getAsInt(), jsonObject.get("month").getAsInt(),
                jsonObject.get("day").getAsInt(), jsonObject.get("hour").getAsInt(), jsonObject.get("minute").getAsInt());
        return localDateTime;
    }
}
