package Manager.History;

import Tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public interface HistoryManager {
    void add(Task newTask);

    LinkedList<Task> getHistory();

    void remove(int id);
}