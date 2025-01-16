package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import helpers.ToDoHelperApache;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JustOkTests {
    private final static String URL = "https://todo-app-sky.herokuapp.com/";

    private final static int CODE_OK = 200;

    private OkHttpClient httpClient;
    public static final MediaType JSON = MediaType.get("application/json");

    @BeforeEach
    public void setUp(){
        httpClient = new OkHttpClient.Builder().build();
    }

    @Test
    @DisplayName("Получение списка задач возвращает код 200 и json")
    public void getTasksCodeOk() throws IOException {
        Request request = new Request.Builder().url(URL).get().build();
        Response response = httpClient.newCall(request).execute();
        System.out.println(response.code());
        System.out.println(response.body().string());
        System.out.println(response.headers());
//        HttpGet httpGet = new HttpGet(URL);
//        HttpResponse httpResponse = httpClient.execute(httpGet);
    }

    @Test
    @DisplayName("Создание таски")
    public void postTasksCodeOk() throws IOException {
        String jsonBodyToSend = "{\n"
            + "    \"title\": \"Я создан с помощью OkHttpClient на Java\"\n"
            + "}";
        Request request = new Request.Builder().url(URL).post(RequestBody.create(jsonBodyToSend, JSON)).build();
        Response response = httpClient.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
