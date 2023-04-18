package Manager;
import Manager.History.*;
import Manager.TaskManager.InMemoryTaskManager;
import Manager.TaskManager.FileBackedTasksManager;


public class Manager {
    public static InMemoryTaskManager getTaskManager() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
    public static InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }


}
