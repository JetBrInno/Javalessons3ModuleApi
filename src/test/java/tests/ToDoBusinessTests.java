package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Task;
import helpers.ToDoHelperApache;
import java.io.IOException;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ToDoBusinessTests {
    private final static String URL = "https://todo-app-sky.herokuapp.com/";

    private final static int CODE_OK = 200;

    private HttpClient httpClient;

    private ToDoHelperApache toDoHelperApache;

    @BeforeEach
    public void setUp(){
        toDoHelperApache = new ToDoHelperApache();

        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    @DisplayName("Создание объекта")
    @Disabled("Тест падает из-за бага задача jira.com/BUG-12523532")
    public void createTask() throws IOException {
        Task myTask = toDoHelperApache.createTask();

        List<Task> tasks = toDoHelperApache.getTasks();
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
        toDoHelperApache.createTask();
        toDoHelperApache.createTask();
        toDoHelperApache.createTask();

        List<Task> tasks = toDoHelperApache.getTasks();
        assertEquals(3, tasks.size());

        System.out.println(tasks);
    }
}
