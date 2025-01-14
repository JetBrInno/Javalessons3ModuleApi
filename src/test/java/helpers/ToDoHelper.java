package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Task;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

public interface ToDoHelper {
    public Task createTask() throws IOException;

    public List<Task> getTasks() throws IOException;
}
