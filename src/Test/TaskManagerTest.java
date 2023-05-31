package Test;

import Manager.TaskManager.TaskManager;
import Tasks.Epic;
import Tasks.SingleTask;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {
    T manager;

    @Test
    void shouldClearAll() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        Epic testEpic1 = new Epic("Title2", "Description2", 2);
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title3", "Description3",
                3, testEpic1.getId(), LocalDateTime.of(2000, 1, 1, 2, 0),
                Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        manager.clearAll();
        Assertions.assertEquals(manager.getAllTasks(), new ArrayList<>());
    }

    @Test
    void shouldGetTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
    }

    @Test
    void shouldGetWrongTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
    }

    @Test
    void shouldSetSingleTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        Assertions.assertEquals(testSingleTask1, manager.getTask(1));
    }

    @Test
    void shouldSetEpic() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Assertions.assertEquals(testEpic1, manager.getTask(1));
    }

    @Test
    void shouldSetSubtask() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title2", "Description2",
                manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Assertions.assertEquals(testSubtask1, manager.getTask(2));
    }

    @Test
    void shouldSetSubtaskWithoutEpic() throws IOException, InterruptedException {
        Subtask testSubtask1 = new Subtask("Title2", "Description2",
                manager.getId(), 3, LocalDateTime.of(2000, 1, 1, 1, 0),
                Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Assertions.assertNull(manager.getTask(2));
    }

    @Test
    void shouldRemoveTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        manager.removeTask(1);
        Assertions.assertNull(manager.getTask(1));
    }

    @Test
    void shouldRemoveWrongTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        manager.removeTask(3);
        Assertions.assertEquals(testSingleTask1, manager.getTask(1));
    }

    @Test
    void shouldUpdateSingleTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        SingleTask testSingleTask2 = new SingleTask("Title2", "Description2", testSingleTask1.getId(),
                LocalDateTime.of(2000, 1, 1, 2, 0), Duration.ofHours(1));
        manager.updateSingleTask(testSingleTask2);
        Assertions.assertEquals(testSingleTask2, manager.getTask(testSingleTask1.getId()));
    }

    @Test
    void shouldUpdateNullSingleTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        SingleTask testSingleTask2 = null;
        manager.updateSingleTask(testSingleTask2);
        Assertions.assertEquals(testSingleTask1, manager.getTask(testSingleTask1.getId()));
    }

    @Test
    void shouldUpdateWrongSingleTask() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        SingleTask testSingleTask2 = new SingleTask("Title2", "Description2", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.updateSingleTask(testSingleTask2);
        Assertions.assertEquals(List.of(testSingleTask1), manager.getAllTasks());
    }

    @Test
    void shouldUpdateEpic() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Epic testEpic2 = new Epic("Title2", "Description2", testEpic1.getId());
        manager.updateEpic(testEpic2);
        Assertions.assertEquals(testEpic2, manager.getTask(testEpic1.getId()));
    }

    @Test
    void shouldUpdateNullEpic() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Epic testEpic2 = null;
        manager.updateEpic(testEpic2);
        Assertions.assertEquals(testEpic1, manager.getTask(testEpic1.getId()));
    }

    @Test
    void shouldUpdateWrongEpic() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Epic testEpic2 = new Epic("Title2", "Description2", manager.getId());
        manager.updateEpic(testEpic2);
        Assertions.assertEquals(List.of(testEpic1), manager.getAllTasks());
    }

    @Test
    void shouldUpdateSubtask() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = new Subtask("Title2", "Description2", testSubtask1.getId(),
                testEpic1.getId(), LocalDateTime.of(2000, 1, 1, 1, 0),
                Duration.ofHours(1));
        manager.updateSubtask(testSubtask2);
        Assertions.assertEquals(testSubtask2, manager.getTask(testSubtask1.getId()));
    }

    @Test
    void shouldUpdateWrongSubtask() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.updateSubtask(testSubtask2);
        Assertions.assertEquals(List.of(testEpic1, testSubtask1), manager.getAllTasks());
    }

    @Test
    void shouldUpdateNullSubtask() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = null;
        manager.updateSubtask(testSubtask2);
        Assertions.assertEquals(List.of(testEpic1, testSubtask1), manager.getAllTasks());
    }

    @Test
    void checkEpicDurationAndStartTime() throws IOException, InterruptedException {
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 3, 0), Duration.ofHours(2));
        manager.setSubtask(testSubtask2);
        Assertions.assertEquals(Duration.ofHours(3), testEpic1.getDuration());
        Assertions.assertEquals(LocalDateTime.of(2000, 1, 1, 1, 0), testEpic1.getStartTime());
    }

    @Test
    void checkGetPrioritizedTasks() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title2", "Description2", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 12, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 15, 0), Duration.ofHours(2));
        manager.setSubtask(testSubtask2);
        LinkedList<Task> checkList = new LinkedList<>();
        checkList.add(testSubtask1);
        checkList.add(testSingleTask1);
        checkList.add(testSubtask2);
        Assertions.assertEquals(checkList.toString(), manager.getPrioritizedTasks().toString());
    }

    @Test
    void checkGetPrioritizedTasksWithNullStartTime() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title2", "Description2", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
                null, Duration.ofHours(1));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 3, 0), Duration.ofHours(2));
        manager.setSubtask(testSubtask2);
        LinkedList<Task> checkList = new LinkedList<>();
        checkList.add(testSingleTask1);
        checkList.add(testSubtask2);
        checkList.add(testSubtask1);
        Assertions.assertEquals(checkList.toString(), manager.getPrioritizedTasks().toString());
    }

    @Test
    void checkAddTaskWithIntersection() throws IOException, InterruptedException {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 5, 0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        Epic testEpic1 = new Epic("Title1", "Description1", manager.getId());
        manager.setEpic(testEpic1);
        Subtask testSubtask1 = new Subtask("Title1", "Description1", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 1, 0), Duration.ofHours(3));
        manager.setSubtask(testSubtask1);
        Subtask testSubtask2 = new Subtask("Title2", "Description2", manager.getId(), testEpic1.getId(),
                LocalDateTime.of(2000, 1, 1, 2, 0), Duration.ofHours(2));
        manager.setSubtask(testSubtask2);
        SingleTask testSingleTask2 = new SingleTask("Title2", "Description2", manager.getId(),
                LocalDateTime.of(2000, 1, 1, 0, 0), Duration.ofHours(2));
        manager.setSingleTask(testSingleTask2);

        LinkedList<Task> checkList = new LinkedList<>();
        checkList.add(testSubtask1);
        checkList.add(testSingleTask1);
        Assertions.assertEquals(checkList.toString(), manager.getPrioritizedTasks().toString());
    }
}
