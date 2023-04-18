package Manager.TaskManager;

import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;

public interface TaskManager {
    LinkedList<Task> getHistoryMemory();

    ArrayList<Task> getAllTasks() ;

    void clearAll() ;

    Task getTask(int id) ;

    void setSingleTask(SingleTask newSingleTask) ;

    void setEpic(Epic newEpic) ;

    void setSubtask(Subtask newSubtask) ;

    void removeTask(int id) ;

    void updateSingleTask(SingleTask updatedSingleTask);

    void updateEpic(Epic updatedEpic);
    void updateSubtask(Subtask updatedSubtask);
}

