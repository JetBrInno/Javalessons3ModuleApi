package x_clients;

import static helpers.ToDoHelperOkHttp.JSON;

import helpers.LoggingInterceptor;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class XClientsOkHttpTests {

    private final static String URL = "https://x-clients-be.onrender.com/";

    private OkHttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
    }

    @Test
    @DisplayName("Получить список компаний")
    public void getCompanyList() throws IOException {
        String token; // ВЗЯТЬ ТОКЕН ИЗ POST АВТОРИЗАЦИИ
        Request request = new Request.Builder().url(URL + "company").addHeader("x-client-token", token).get().build();
        Response response = httpClient.newCall(request).execute();

        String body = response.body().string();
        System.out.println(body);
    }

    @Test
    @DisplayName("Проверка авторизации")
    public void authorization() throws IOException {
        String jsonBodyToSend = """
             {
               "username": "leonardo",
               "password": "leads"
             }
            """;
        Request request = new Request.Builder().url(URL + "auth/login").post(RequestBody.create(jsonBodyToSend, JSON))
            .build();
        Response response = httpClient.newCall(request).execute();

        String body = response.body().string();
        System.out.println(body);
    }
}
