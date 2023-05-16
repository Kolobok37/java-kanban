package Test;

import Manager.Manager;
import Manager.TaskManager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

public class TaskManagerInMemoryTest extends TaskManagerTest<InMemoryTaskManager>{
    @BeforeEach
    public void createManager(){
        manager = new InMemoryTaskManager(Manager.getDefaultHistory());
    }
}
