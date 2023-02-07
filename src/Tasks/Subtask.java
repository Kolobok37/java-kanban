package Tasks;

public class Subtask extends Task{
    private int idEpic;

    public Subtask(String title, String description, int id, int idEpic) {
        super(title, description, id);
        this.idEpic = idEpic;
        status="NEW";
    }

    @Override
    public String toString() {
        return "Subtask" +
                " id = " + id+
                ", title= '" + super.title + "'" +
                ", status= '" + status + "'";
    }

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }
}
