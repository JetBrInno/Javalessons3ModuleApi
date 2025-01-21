package x_clients.rest_assured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompanyContractTest {

    private final static String URL = "https://x-clients-be.onrender.com/";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = URL;
    }

    // given
    // when
    // then
    @Test
    @DisplayName("Код ответа при получении списка компаний")
    public void getCompanyList() {
        given()  // ДАНО:
            .basePath("company")
            .when()     // КОГДА
            .get() // ШЛЕШЬ ГЕТ ЗАПРОС
            .then() // ТОГДА ПРОВЕРЬ СЛЕДУЮЩЕЕ:
            .statusCode(200)
            .header("Content-Type", "application/json; charset=utf-8");

//        RequestSpecification requestSpecification = given().baseUri(URL + "company");
//        Response response = requestSpecification.when().get();
//        ValidatableResponse validatableResponse = response
//            .then()
//            .statusCode(200)
//            .header("Content-Type","application/json; charset=utf-8");;
    }

    @Test
    @DisplayName("Создание компании")
    public void createCompany() {
        String userToken = authAndGetToken();

        String jsonBodyToSend = """
            {
              "name": "RestAssuredCompany",
              "description": "MyRestAssuredCompany"
            }
            """;
        String id = given()  // ДАНО:
            .basePath("company")
            .body(jsonBodyToSend)
            .contentType(ContentType.JSON)
            .header("x-client-token", userToken)
            .when()     // КОГДА
            .post() // ШЛЕШЬ ПОСТ ЗАПРОС
            .then()
            .statusCode(201)
            .body("id", is(greaterThan(0)))
            .extract().jsonPath().getString("id");

        System.out.println(id);
    }

    private String authAndGetToken() {
        String jsonBodyToSend = """
             {
               "username": "leonardo",
               "password": "leads"
             }
            """;

        return given()  // ДАНО:
            .basePath("auth/login")
            .body(jsonBodyToSend)
            .contentType(ContentType.JSON)
            .when()     // КОГДА
            .post() // ШЛЕШЬ ПОСТ ЗАПРОС
            .jsonPath().getString("userToken");
    }
}
