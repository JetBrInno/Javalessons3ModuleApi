package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Task;
import helpers.ToDoHelper;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ToDoContractTests {
    private final static String URL = "https://todo-app-sky.herokuapp.com/";

    private final static int CODE_OK = 200;

    private HttpClient httpClient;

    private ToDoHelper toDoHelper;

    @BeforeEach
    public void setUp(){
        toDoHelper = new ToDoHelper();
        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    @DisplayName("Получение списка задач возвращает код 200 и json")
    public void getTasksCodeOk() throws IOException {
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        // достаем тело из ответа
        String body = EntityUtils.toString(httpResponse.getEntity());
        assertEquals(CODE_OK, httpResponse.getStatusLine().getStatusCode());
        assertTrue(body.startsWith("["));
        assertTrue(body.endsWith("]"));
    }

    @Test
    @DisplayName("Получение списка задач возвращает все поля валидных типов")
    public void getTasksBodyValidTypes() throws IOException {
        toDoHelper.createTask();

        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        // достаем тело из ответа и преобразуем в сущность
        String body = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Task> tasks = List.of(objectMapper.readValue(body, Task[].class));
        assertFalse(tasks.isEmpty());

        System.out.println(tasks);
    }
}
