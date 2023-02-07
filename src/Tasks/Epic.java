package Tasks;

import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList <Integer> subtasksId = new ArrayList<>();
    private ArrayList <Subtask> subtasks = new ArrayList<>();

        public Epic(String title, String description, int id) {

        super(title, description, id);
    }

    @Override
    public String toString() {
        return "Epic " +
                "id = " + super.id +
                ": title= '" + super.title + "'"+
                ", status= '" + status + '\'' +
                ", subtask: " + subtasksId.size();
    }


    public ArrayList<Integer> getSubtasksId() {
        return new ArrayList<>(subtasksId);
    }

    public void addSubtaskId(int idSubtask) {
        subtasksId.add(idSubtask);
    }

    public  void removeSubtask(int idSubtask){
        subtasksId.remove(subtasksId.indexOf(idSubtask));
    }

    public void addSubtask(Subtask newSubtask) {
        subtasks.add(newSubtask);
    }
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }
    @Override
    public void setStatus(String statusSubtask) {
            switch (statusSubtask){
                case "NEW":
                    if(!status.equals("NEW")){
                        status= "IN_PROGRESS";
                    }
                    else {
                    status="NEW";
                    }
                    break;
                case "DONE":
                    if(!status.equals("DONE")){
                        for(int i = 0; i<subtasksId.size(); i++){
                            if(subtasks.get(i).getStatus().equals("DONE")){
                                status = "DONE";
                            }
                            else {
                                status ="IN_PROGRESS";
                                break;
                            }
                        }

                    }
                    break;
                case "IN_PROGRESS":
                    status = "IN_PROGRESS";
                    break;
            }
        }
    }
