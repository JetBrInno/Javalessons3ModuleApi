package tests;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;

import entities.Task;
import java.io.IOException;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToDoListTest {

    private final static String URL = "https://todo-app-sky.herokuapp.com/";

    private final static int CODE_OK = 200;

    private HttpClient httpClient;

    @BeforeEach
    public void setUp(){
        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    public void getTasksCodeOk() throws IOException {
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        assertEquals(CODE_OK, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void getTasksJson() throws IOException {
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        Header contentType = httpResponse.getFirstHeader("Content-Type");
        assertEquals("application/json; charset=utf-8", contentType.getValue());
    }

    @Test
    public void instancioTest() {
        // Создать случ. задачу
        Task task = Instancio.create(Task.class);
        //System.out.println(task);

        // Более гибкая настройка объекта
        Task task1 = Instancio.of(Task.class).generate(field("title"), generators -> generators.text().loremIpsum()).create();
        System.out.println(task1);

        // Создать 10 случ. задач
        List<Task> tasks = Instancio.ofList(Task.class).size(10).create();
        //System.out.println(tasks);
    }

}
