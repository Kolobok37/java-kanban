package Manager.TaskManager;

import Manager.History.InMemoryHistoryManager;
import Manager.Manager;
import Tasks.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {
    File file;

    public FileBackedTasksManager(InMemoryHistoryManager history, File file) {
        super(history);
        this.file = file;
    }

    public static void main(String[] args) {
        FileBackedTasksManager manager = new FileBackedTasksManager(Manager.getDefaultHistory(),
                new File("TaskManager"));

        SingleTask testSingleTask1 = new SingleTask("Мыть пол", "Помыть до 21:00", manager.getId(),
                LocalDateTime.of(2000,1,1,7,0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        Epic testEpic1 = new Epic("Сделать ремонт", "В квартире", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                manager.getId(), testEpic1.getId(), LocalDateTime.of(2000,1,1,3,0),
                Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = new Subtask("Покрасить стены", "Стройматериалы",
                manager.getId(), testEpic1.getId(), LocalDateTime.of(2000,1,1,5,0),
                Duration.ofHours(1));
        manager.setSubtask(testSubtask2);
        manager.getTask(1);
        System.out.println("Отсортированный список задач:");
        System.out.println(manager.getPrioritizedTasks());

        FileBackedTasksManager manager2 = new FileBackedTasksManager(Manager.getDefaultHistory(),
                new File("TaskManager"));
        manager2.load();
        System.out.println("Все задачи после открытия файла:");
        System.out.println(manager2.getAllTasks());
        System.out.println("История после открытия файла:");
        System.out.println(manager2.getHistoryMemory());
        System.out.println("Отсортированный список задач после открытия файла:");
        System.out.println(manager2.getPrioritizedTasks());
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public void setEpic(Epic newEpic){    //Id Task присваевается в конструкторе и передаётся в метод уже с id,
        super.setEpic(newEpic);            // на данный момент логика такая.
        save();
    }


    @Override
    public void setSingleTask(SingleTask newSingleTask){
        super.setSingleTask(newSingleTask);
        save();
    }

    @Override
    public void setSubtask(Subtask newSubtask){
        super.setSubtask(newSubtask);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            List<Task> allTask = super.getAllTasks();
            writer.write("id,type,name,status,description,epic\n");
            for (Task task : allTask) {
                writer.write(task.toString() + "\n");
            }
            writer.write("\n" + historyToString(super.getHistoryMemory()));
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Ошибка записи вырисовывается(");
            } catch (ManagerSaveException exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    private void loadEpic(Epic newEpic) {
        epicList.put(newEpic.getId(), newEpic);
    }

    private void loadSingleTask(SingleTask newSingleTask) {
        taskList.put(newSingleTask.getId(), newSingleTask);
    }

    private void loadSubtask(Subtask newSubtask) {
        subtaskList.put(newSubtask.getId(), newSubtask);
        epicList.get(newSubtask.getIdEpic()).addSubtask(newSubtask);
        epicList.get(newSubtask.getIdEpic()).setStatus();
    }

    public void load() {
        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            String line = buf.readLine();
            line = buf.readLine();
            while (!line.isEmpty()) {
                //System.out.println("линия" +line);
                Task task = fromString(line);
                //System.out.println("zadaca"+task);
                line = buf.readLine();
                switch (task.getType()) {
                    case Epic: {
                        loadEpic(((Epic) task));
                        break;
                    }
                    case SingleTask: {
                        loadSingleTask(((SingleTask) task));
                        break;
                    }
                    case Subtask: {
                        loadSubtask(((Subtask) task));
                        break;
                    }
                }
            }
            line = buf.readLine();
            if (line!=null) {
                List<Integer> history = historyFromString(line);
                for (Integer i : history) {
                    getTask(i);
                }
            }
        } catch (IOException e) {
            try {
                throw new ManagerSaveException("Ошибка ввода вырисовывается(");
            } catch (ManagerSaveException exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    private Task fromString(String value) {
        String[] lineTask = value.split(",");
        try {
            String status = lineTask[3];
            TaskStatus statusEnum = TaskStatus.NEW;
            for (TaskStatus statusTask : TaskStatus.values()) {
                if (status.equals(statusTask.toString())) {
                    statusEnum = statusTask;
                }
            }
            switch (lineTask[1]) {
                case ("SingleTask"):
                    SingleTask singleTask = new SingleTask(lineTask[2], lineTask[4], Integer.parseInt(lineTask[0]), LocalDateTime.parse(lineTask[5]),Duration.parse(lineTask[6]));
                    singleTask.setStatus(statusEnum);
                    return singleTask;
                case ("Epic"):
                    Epic epic = new Epic(lineTask[2], lineTask[4], Integer.parseInt(lineTask[0]),LocalDateTime.parse(lineTask[5]),Duration.parse(lineTask[6]));
                    epic.loadStatus(statusEnum);
                    return epic;
                case ("Subtask"):
                    Subtask subtask = new Subtask(lineTask[2], lineTask[4], Integer.parseInt(lineTask[0]), Integer.parseInt(lineTask[5]),LocalDateTime.parse(lineTask[6]),Duration.parse(lineTask[7]));
                    subtask.setStatus(statusEnum);
                    return subtask;
                default:
                    throw new RuntimeException("Ошибка чтения файла");
            }
        } catch (RuntimeException e) {
            try {
                throw new ManagerSaveException("Ошибка чтения файла");
            } catch (ManagerSaveException exp) {
                System.out.println(exp.getMessage());
            }
        }
        return null;
    }

    private static String historyToString(LinkedList<Task> history) {
        String[] historyIdArray = new String[history.size()];
        int i = 0;
        for (Task task : history) {
            historyIdArray[i] = Integer.toString(task.getId());
            i++;
        }
        return String.join(", ", historyIdArray);
    }

    private static List<Integer> historyFromString(String historyId) {
        String[] historyArrayStr = historyId.split(", ");
        Integer[] historyArrayInt = new Integer[historyArrayStr.length];
        for (int i = 0; i < historyArrayStr.length; i++) {
            historyArrayInt[i] = Integer.parseInt(historyArrayStr[i]);
        }
        return Arrays.asList(historyArrayInt);
    }

    public File getFile() {
        return file;
    }
}

