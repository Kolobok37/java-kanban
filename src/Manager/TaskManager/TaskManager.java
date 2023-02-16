package Manager.TaskManager;

import Manager.History.InMemoryHistoryManager;
import Tasks.*;

import java.util.ArrayList;

public interface TaskManager {
    int getId();

    ArrayList<Task> getHistoryMemory();

    ArrayList<Task> getAllTasks() ;

    void clearAll() ;

    Task getTask(int id) ;

    void setSingleTask(SingleTask newSingleTask) ;

    void setEpic(Epic newEpic) ;

    void setSubtask(Subtask newSubtask) ;

    boolean removeTask(int id) ;

    void updateSingleTask(SingleTask updatedSingleTask);

    void updateEpic(Epic updatedEpic);
    void updateSubtask(Subtask updatedSubtask);
}

