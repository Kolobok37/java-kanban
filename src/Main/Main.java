package Main;

import Manager.TaskManager;
import Tasks.*;
public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task testTask1=new Task("Мыть пол", "Помыть до 21:00", manager.getId());
        manager.setTask(testTask1);

        Task testTask2=new Task("Мыть ванну", "Помыть до 22:00", manager.getId());
        manager.setTask(testTask2);

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
        testSubtask1.setStatus("DONE");
        manager.updateSubtask(testSubtask1);
        testSubtask2.setStatus("DONE");
        System.out.println(manager.getAllTasks());
        manager.updateSubtask(testSubtask2);
        testTask1.setStatus("DONE");
        manager.updateTask(testTask1);
        System.out.println(manager.getAllTasks());
        manager.removeTask(1);
        manager.removeTask(3);
        System.out.println(manager.getAllTasks());
    }
}