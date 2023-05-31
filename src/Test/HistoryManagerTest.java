package Test;

import Manager.Manager;
import Manager.TaskManager.InMemoryTaskManager;
import Tasks.SingleTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

class HistoryManagerTest {
    InMemoryTaskManager manager;

    @BeforeEach
    public void createManager(){
        manager = new InMemoryTaskManager(Manager.getDefaultHistory());
    }

    @Test
    void shouldAddAndGet() {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        manager.getTask(1);
        Assertions.assertEquals(List.of(testSingleTask1),manager.getHistoryMemory());
    }

    @Test
    void  shouldAddDoubleTask() {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000,1,1,1,0),Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        manager.getTask(1);
        manager.getTask(1);
        Assertions.assertEquals(List.of(testSingleTask1),manager.getHistoryMemory());
    }

    @Test
    void shouldGetNullHistory() {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        Assertions.assertEquals(List.of(),manager.getHistoryMemory());
    }

    @Test
    void shouldRemove() {
        SingleTask testSingleTask1 = new SingleTask("Title1", "Description1", 1,
                LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        SingleTask testSingleTask2 = new SingleTask("Title2", "Description2", 2,
                LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
        SingleTask testSingleTask3 = new SingleTask("Title3", "Description3", 3,
                LocalDateTime.of(2000,1,1,3,0), Duration.ofHours(1));
        SingleTask testSingleTask4 = new SingleTask("Title4", "Description4", 4,
                LocalDateTime.of(2000,1,1,4,0), Duration.ofHours(1));
        manager.setSingleTask(testSingleTask1);
        manager.setSingleTask(testSingleTask2);
        manager.setSingleTask(testSingleTask3);
        manager.setSingleTask(testSingleTask4);
        manager.getTask(1);
        manager.getTask(2);
        manager.getTask(3);
        manager.getTask(4);
        manager.removeTask(1);
        Assertions.assertEquals(List.of(testSingleTask2,testSingleTask3,testSingleTask4),manager.getHistoryMemory());
        manager.removeTask(3);
        Assertions.assertEquals(List.of(testSingleTask2,testSingleTask4),manager.getHistoryMemory());
        manager.removeTask(2);
        Assertions.assertEquals(List.of(testSingleTask4),manager.getHistoryMemory());
    }
}