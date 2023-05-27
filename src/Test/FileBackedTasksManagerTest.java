package Test;

import Manager.Manager;
import Manager.TaskManager.FileBackedTasksManager;
import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Subtask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{
//    @BeforeEach
//    void createManager(){
//        manager = new FileBackedTasksManager(Manager.getDefaultHistory(), new File("TaskManager"));
//    }
//
//    @Test
//    void saveAndLoadTaskWithoutHistory(){
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1, LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        Epic testEpic1 = new Epic("Title2", "Description2",2);
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title3", "Description3",
//                3, testEpic1.getId(),LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        FileBackedTasksManager manager2 = new FileBackedTasksManager(Manager.getDefaultHistory(), new File("TaskManager"));
//        manager2.load();
//        Assertions.assertEquals(manager2.getAllTasks().toString(), manager.getAllTasks().toString());
//    }
//    @Test
//    void saveAndLoadHistory(){
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        Epic testEpic1 = new Epic("Title2", "Description2",2);
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title3", "Description3",
//                3, testEpic1.getId(),LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        manager.getTask(1);
//        manager.getTask(2);
//        FileBackedTasksManager manager2 = new FileBackedTasksManager(Manager.getDefaultHistory(), new File("TaskManager"));
//        manager2.load();
//        Assertions.assertEquals(manager2.getHistoryMemory().toString(),manager.getHistoryMemory().toString());
//    }
//    @Test
//    void saveAndLoadTaskEmptyList(){
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        manager.removeTask(1);
//        FileBackedTasksManager manager2 = new FileBackedTasksManager(Manager.getDefaultHistory(), new File("TaskManager"));
//        manager2.load();
//        Assertions.assertEquals(List.of().toString(),manager2.getAllTasks().toString().toString());
//    }
//
//    @Test
//    void saveAndLoadEpicWithoutSubtask(){
//        Epic testEpic1 = new Epic("Title2", "Description2",2);
//        manager.setEpic(testEpic1);        manager.setEpic(testEpic1);
//        FileBackedTasksManager manager2 = new FileBackedTasksManager(Manager.getDefaultHistory(), new File("TaskManager"));
//        manager2.load();
//        Assertions.assertEquals(List.of(testEpic1).toString(),manager2.getAllTasks().toString());
//    }
}
