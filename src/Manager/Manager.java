package Manager;
import Manager.History.*;
import Manager.TaskManager.InMemoryTaskManager;

public class Manager {
    public static InMemoryTaskManager getTaskManager() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
    public static InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }


}
