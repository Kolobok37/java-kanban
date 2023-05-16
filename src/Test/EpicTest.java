package Test;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;


public class EpicTest {
    Epic testEpic;

    @BeforeEach
    public void createEpic() {
        testEpic = new Epic("Сделать ремонт", "В квартире", 0);
    }

    @Test
    public void checkEpicStatusWithoutSubtask() {
        testEpic.setStatus();
        Assertions.assertEquals(testEpic.getStatus(), TaskStatus.NEW);
    }

    @Test
    public void checkEpicStatusWithSubtaskStatusNEW() {
        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                1, 0, LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        Subtask testSubtask2 = new Subtask("Покрасить стены", "В синий",
                2, 0,LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
        testEpic.addSubtask(testSubtask1);
        testEpic.addSubtask(testSubtask2);
        testEpic.setStatus();
        Assertions.assertEquals(testEpic.getStatus(), TaskStatus.NEW);

    }

    @Test
    public void checkEpicStatusWithSubtaskStatusDONE() {
        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                1, 0,LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        Subtask testSubtask2 = new Subtask("Покрасить стены", "В синий",
                2, 0,LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
        testSubtask1.setStatus(TaskStatus.DONE);
        testSubtask2.setStatus(TaskStatus.DONE);
        testEpic.addSubtask(testSubtask1);
        testEpic.addSubtask(testSubtask2);
        testEpic.setStatus();
        Assertions.assertEquals(testEpic.getStatus(), TaskStatus.DONE);
    }

    @Test
    public void checkEpicStatusWithSubtaskStatusNEWAndDONE() {
        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                1, 0,LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        Subtask testSubtask2 = new Subtask("Покрасить стены", "В синий",
                2, 0,LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
        testSubtask2.setStatus(TaskStatus.DONE);
        testEpic.addSubtask(testSubtask1);
        testEpic.addSubtask(testSubtask2);
        testEpic.setStatus();
        Assertions.assertEquals(testEpic.getStatus(), TaskStatus.IN_PROGRESS);
    }

    @Test
    public void checkEpicStatusWithSubtaskStatusIN_PROGRESS() {
        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                1, 0,LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        Subtask testSubtask2 = new Subtask("Покрасить стены", "В синий",
                2, 0,LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
        testSubtask1.setStatus(TaskStatus.IN_PROGRESS);
        testSubtask2.setStatus(TaskStatus.IN_PROGRESS);
        testEpic.addSubtask(testSubtask1);
        testEpic.addSubtask(testSubtask2);
        testEpic.setStatus();
        Assertions.assertEquals(testEpic.getStatus(), TaskStatus.IN_PROGRESS);
    }
    @Test
    public void checkTime(){
        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                1, 0,LocalDateTime.of(2000,1,1,1,0), Duration.ofHours(1));
        Subtask testSubtask2 = new Subtask("Покрасить стены", "В синий",
                2, 0,LocalDateTime.of(2000,1,1,2,0), Duration.ofHours(1));
        testEpic.addSubtask(testSubtask1);
        testEpic.addSubtask(testSubtask2);
        testEpic.subtasks.values().stream().sorted((p1, p2)->p2.startTime.compareTo(p1.startTime)).forEach(System.out::println);
    }
}
