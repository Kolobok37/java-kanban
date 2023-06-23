package Manager.TaskManager.JsonAdapter;

import Tasks.Epic;
import Tasks.SingleTask;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EpicDeserializer implements JsonDeserializer<Epic> {
    @Override
    public Epic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Epic epic = null;
        if (jsonObject.getAsJsonObject().get("type").getAsString().equals("Epic")) {
            List<Integer> listSubtask= new ArrayList<>();
            String strSubtaskId = jsonObject.getAsJsonObject().get("subtaskId").getAsString();
            if(strSubtaskId.length()!=2){
                listSubtask=Arrays.stream(jsonObject.getAsJsonObject().get("subtaskId").getAsString().
                        substring(1, jsonObject.getAsJsonObject().get("subtaskId").getAsString().length()-1)
                        .split(", ")).map(id->Integer.parseInt(id)).collect(Collectors.toList());
            }
            epic = new Epic(jsonObject.getAsJsonObject().get("title").getAsString(),
                    jsonObject.getAsJsonObject().get("description").getAsString(),
                    jsonObject.getAsJsonObject().get("id").getAsInt(),
                    context.deserialize(jsonObject.getAsJsonObject().get("startTime"), LocalDateTime.class),
                    context.deserialize(jsonObject.getAsJsonObject().get("duration"), Duration.class),
                    listSubtask,
            jsonObject.getAsJsonObject().get("status").getAsString());

        }
        return epic;
    }
}
