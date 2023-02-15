package Manager.History;

import Tasks.Task;
import java.util.ArrayList;
public interface HistoryManager {
    void addTaskInHistory(Task newTask);
    ArrayList<Task> getHistory();
}