package Manager.HistoryManager;

import Tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    ArrayList<Task> historyArray = new ArrayList<>();

    @Override
    public ArrayList<Task> getHistory(){
        return new ArrayList<Task>(historyArray);
    }
    @Override
    public void addTaskInHistory(Task newTask){
        historyArray.add(newTask);
        if(historyArray.size()>10){
            historyArray.remove(0);
        }
    }
}
