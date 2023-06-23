package Manager.TaskManager;

import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Subtask;
import Tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

public interface TaskManager {
    LinkedList<Task> getHistoryMemory();

    ArrayList<Task> getAllTasks() ;

    void clearAll() ;

    Task getTask(int id) ;

    void setSingleTask(SingleTask newSingleTask) throws IOException, InterruptedException;

    void setEpic(Epic newEpic) throws IOException, InterruptedException;

    void setSubtask(Subtask newSubtask) throws IOException, InterruptedException;

    void removeTask(int id) ;

    void updateSingleTask(SingleTask updatedSingleTask);

    void updateEpic(Epic updatedEpic);
    void updateSubtask(Subtask updatedSubtask);

    int getId();
    TreeSet getPrioritizedTasks();

}

