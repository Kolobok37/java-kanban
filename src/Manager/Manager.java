package Manager;
import Manager.HistoryManager.*;

public abstract class Manager {
    public static InMemoryTaskManager getTaskManager() {
        return new InMemoryTaskManager(getDefaultHistory());
    }
    public static InMemoryHistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }


}
