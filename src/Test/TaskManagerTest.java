package Test;

import Manager.TaskManager.TaskManager;
import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {
    T manager;

//    @Test
//    void shouldClearAll() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        Epic testEpic1 = new Epic("Title2", "Description2", 2);
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title3", "Description3",
//                3, testEpic1.getId(), LocalDateTime.of(2000, 1, 1, 2, 0),
//                Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        manager.clearAll();
//        Assertions.assertEquals(manager.getAllTasks(), new ArrayList<>());
//    }
//
//    @Test
//    void shouldGetTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//    }
//
//    @Test
//    void shouldGetWrongTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//    }
//
//    @Test
//    void shouldSetSingleTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        Assertions.assertEquals(testSingleTask1, manager.getTask(1));
//    }
//
//    @Test
//    void shouldSetEpic() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Assertions.assertEquals(testEpic1, manager.getTask(1));
//    }
//
//    @Test
//    void shouldSetSubtask() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title2", "Description2",
//                manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Assertions.assertEquals(testSubtask1, manager.getTask(2));
//    }
//
//    @Test
//    void shouldSetSubtaskWithoutEpic() {
//        Subtask testSubtask1 = new Subtask("Title2", "Description2",
//                manager.getId(), 3, LocalDateTime.of(2000, 1, 1, 1, 0),
//                Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Assertions.assertNull(manager.getTask(2));
//    }
//
//    @Test
//    void shouldRemoveTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        manager.removeTask(1);
//        Assertions.assertNull(manager.getTask(1));
//    }
//
//    @Test
//    void shouldRemoveWrongTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        manager.removeTask(3);
//        Assertions.assertEquals(testSingleTask1, manager.getTask(1));
//    }
//
//    @Test
//    void shouldUpdateSingleTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        SingleTask testSingleTask2 = new SingleTask("Title2", "Description2", testSingleTask1.getId(),
//                LocalDateTime.of(2000, 1, 1, 2, 0), Duration.ofHours(1));
//        manager.updateSingleTask(testSingleTask2);
//        Assertions.assertEquals(testSingleTask2, manager.getTask(testSingleTask1.getId()));
//    }
//
//    @Test
//    void shouldUpdateNullSingleTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        SingleTask testSingleTask2 = null;
//        manager.updateSingleTask(testSingleTask2);
//        Assertions.assertEquals(testSingleTask1, manager.getTask(testSingleTask1.getId()));
//    }
//
//    @Test
//    void shouldUpdateWrongSingleTask() {
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        SingleTask testSingleTask2 = new SingleTask("Title2", "Description2", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.updateSingleTask(testSingleTask2);
//        Assertions.assertEquals(List.of(testSingleTask1), manager.getAllTasks());
//    }
//
//    @Test
//    void shouldUpdateEpic() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Epic testEpic2 = new Epic("Title2", "Description2", testEpic1.getId());
//        manager.updateEpic(testEpic2);
//        Assertions.assertEquals(testEpic2, manager.getTask(testEpic1.getId()));
//    }
//
//    @Test
//    void shouldUpdateNullEpic() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Epic testEpic2 = null;
//        manager.updateEpic(testEpic2);
//        Assertions.assertEquals(testEpic1, manager.getTask(testEpic1.getId()));
//    }
//
//    @Test
//    void shouldUpdateWrongEpic() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Epic testEpic2 = new Epic("Title2", "Description2", manager.getId());
//        manager.updateEpic(testEpic2);
//        Assertions.assertEquals(List.of(testEpic1), manager.getAllTasks());
//    }
//
//    @Test
//    void shouldUpdateSubtask() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Subtask testSubtask2 = new Subtask("Title2", "Description2", testSubtask1.getId(),
//                testEpic1.getId(), LocalDateTime.of(2000, 1, 1, 1, 0),
//                Duration.ofHours(1));
//        manager.updateSubtask(testSubtask2);
//        Assertions.assertEquals(testSubtask2, manager.getTask(testSubtask1.getId()));
//    }
//
//    @Test
//    void shouldUpdateWrongSubtask() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.updateSubtask(testSubtask2);
//        Assertions.assertEquals(List.of(testEpic1, testSubtask1), manager.getAllTasks());
//    }
//
//    @Test
//    void shouldUpdateNullSubtask() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Subtask testSubtask2 = null;
//        manager.updateSubtask(testSubtask2);
//        Assertions.assertEquals(List.of(testEpic1, testSubtask1), manager.getAllTasks());
//    }
//
//    @Test
//    void checkEpicDurationAndStartTime() {
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 3, 0), Duration.ofHours(2));
//        manager.setSubtask(testSubtask2);
//        Assertions.assertEquals(Duration.ofHours(3), testEpic1.getDuration());
//        Assertions.assertEquals(LocalDateTime.of(2000, 1, 1, 1, 0), testEpic1.getStartTime());
//    }
//
//    @Test
//    void checkGetPrioritizedTasks() {
//        SingleTask testSingleTask1 = new SingleTask("Title2", "Description2", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 12, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 15, 0), Duration.ofHours(2));
//        manager.setSubtask(testSubtask2);
//        LinkedList<Task> checkList = new LinkedList<>();
//        checkList.add(testSubtask1);
//        checkList.add(testSingleTask1);
//        checkList.add(testSubtask2);
//        Assertions.assertEquals(checkList.toString(), manager.getPrioritizedTasks().toString());
//    }
//
//    @Test
//    void checkGetPrioritizedTasksWithNullStartTime() {
//        SingleTask testSingleTask1 = new SingleTask("Title2", "Description2", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
//                null, Duration.ofHours(1));
//        manager.setSubtask(testSubtask1);
//        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 3, 0), Duration.ofHours(2));
//        manager.setSubtask(testSubtask2);
//        LinkedList<Task> checkList = new LinkedList<>();
//        checkList.add(testSingleTask1);
//        checkList.add(testSubtask2);
//        checkList.add(testSubtask1);
//        Assertions.assertEquals(checkList.toString(), manager.getPrioritizedTasks().toString());
//    }
//
//    @Test
//    void checkAddTaskWithIntersection(){
//        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 5, 0), Duration.ofHours(1));
//        manager.setSingleTask(testSingleTask1);
//        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
//        manager.setEpic(testEpic1);
//        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(3));
//        manager.setSubtask(testSubtask1);
//        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
//                LocalDateTime.of(2000, 1, 1, 2, 0), Duration.ofHours(2));
//        manager.setSubtask(testSubtask2);
//        SingleTask testSingleTask2 = new SingleTask("Title2", "Description2", manager.getId(),
//                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofHours(2));
//        manager.setSingleTask(testSingleTask2);
//
//        LinkedList<Task> checkList = new LinkedList<>();
//        checkList.add(testSubtask1);
//        checkList.add(testSingleTask1);
//        Assertions.assertEquals(checkList.toString(), manager.getPrioritizedTasks().toString());
//    }
}
