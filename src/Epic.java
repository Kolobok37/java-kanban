import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList <Integer> subtask = new ArrayList<>();

    public Epic(String title, String description, int id) {

        super(title, description, id);
        status="NEW";
    }

    @Override
    public String toString() {
        return "Epic " +
                "id = " + super.id +
                ": title= '" + super.title + "'"+
                ", status= '" + status + '\'' +
                ", subtask: " + subtask.size();
    }

    public ArrayList<Integer> getSubtasks() {
        return subtask;
    }

    public void addSubtask(int idSubtask) {
        subtask.add(idSubtask);
    }

    public  void removeSubtask(int idSubtask){
        for(int i=0; i<subtask.size(); i++) {
            if (subtask.get(i).equals(idSubtask)) {
                subtask.remove(i);
            }
        }
    }
}
