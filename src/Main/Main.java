package Main;

import Manager.TaskManager.InMemoryTaskManager;
import Manager.Manager;
import Tasks.*;
public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager =Manager.getTaskManager();

        SingleTask testSingleTask1 =new SingleTask("Мыть пол", "Помыть до 21:00", manager.getId());
        manager.setSingleTask(testSingleTask1);

        SingleTask testSingleTask2 =new SingleTask("Мыть ванну", "Помыть до 22:00", manager.getId());
        manager.setSingleTask(testSingleTask2);

        Epic testEpic1 = new Epic("Сделать ремонт", "В квартире",manager.getId());
        manager.setEpic(testEpic1);

        Epic testEpic2 = new Epic("Приготовиться к празднику", "К дню рождения подруги",manager.getId());
        manager.setEpic(testEpic2);

        Subtask testSubtask1 = new Subtask("Составить список", "Стройматериалы",
                manager.getId(),testEpic1.getId());
        manager.setSubtask(testSubtask1);

        Subtask testSubtask2 = new Subtask("Нанять рабочих", "3 чел.",
                manager.getId(),testEpic1.getId());
        manager.setSubtask(testSubtask2);

        Subtask testSubtask3 = new Subtask("Купить подарок", "до 3к рублей",
                manager.getId(),testEpic2.getId());
        manager.setSubtask(testSubtask3);

        System.out.println(manager.getAllTasks());
        testSubtask1.setStatus(TaskStatus.DONE);
        manager.updateSubtask(testSubtask1);
        testSubtask2.setStatus(TaskStatus.DONE);
        manager.updateSubtask(testSubtask2);
        testSingleTask1.setStatus(TaskStatus.DONE);
        manager.updateSingleTask(testSingleTask1);
        manager.removeTask(1);
        manager.removeTask(7);
        System.out.println(manager.getAllTasks());
        manager.getTask(2);
        manager.getTask(5);
        manager.getTask(6);
        manager.getTask(1);
        manager.getTask(2);
        manager.getTask(6);
        manager.getTask(5);
        manager.removeTask(1);
        System.out.println(manager.getHistoryMemory());
    }
}