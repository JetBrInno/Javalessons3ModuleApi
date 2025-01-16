package helpers;

import entities.Task;
import java.io.IOException;
import java.util.List;


public interface ToDoHelper {
    public Task createTask() throws IOException;

    public List<Task> getTasks() throws IOException;

    public void deleteTask(Task task) throws IOException;
}
