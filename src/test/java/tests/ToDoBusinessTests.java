package tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Task;
import helpers.ToDoHelper;
import helpers.ToDoHelperApache;
import helpers.ToDoHelperOkHttp;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ToDoBusinessTests {
    private ToDoHelper toDoHelper;

    @BeforeEach
    public void setUp(){
        toDoHelper = new ToDoHelperOkHttp();
    }

    @Test
    @DisplayName("Создание объекта")
    @Disabled("Тест падает из-за бага задача jira.com/BUG-12523532")
    public void createTask() throws IOException {
        Task myTask = toDoHelper.createTask();

        List<Task> tasks = toDoHelper.getTasks();
        for (Task task : tasks) {
            if (task.getId() == myTask.getId())
            {
                assertNotNull(task);
                assertFalse(task.isCompleted());
            }
        }
    }

    @Test
    @DisplayName("Получение списка задач из 3 элементов")
    public void getTasksBodyValidTypes() throws IOException {
        // удалить все элементы
        toDoHelper.createTask();
        toDoHelper.createTask();
        toDoHelper.createTask();

        List<Task> tasks = toDoHelper.getTasks();
        assertEquals(3, tasks.size());

        System.out.println(tasks);
    }

    @Test
    @DisplayName("Удаление задачи")
    public void deleteTask() throws IOException {
        Task myTask = toDoHelper.createTask();
        toDoHelper.deleteTask(myTask);

        List<Task> tasks = toDoHelper.getTasks();
        assertThat(tasks).doesNotContain(myTask);
    }
}
