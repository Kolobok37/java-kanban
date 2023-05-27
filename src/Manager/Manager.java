package Manager;
import Manager.History.*;
import Manager.TaskManager.InMemoryTaskManager;
import Manager.TaskManager.FileBackedTasksManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;


public class Manager {
    public static InMemoryTaskManager getTaskManager() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
    public static InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

}
