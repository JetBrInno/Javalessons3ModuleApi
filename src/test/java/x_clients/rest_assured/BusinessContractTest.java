package x_clients.rest_assured;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import x_clients.rest_assured.entity.Company;
import x_clients.rest_assured.entity.CreateCompanyRequest;
import x_clients.rest_assured.entity.CreateCompanyResponse;
import x_clients.rest_assured.ext.ApiCompanyHelperResolver;
import x_clients.rest_assured.ext.CreateCompanyResponseResolver;
import x_clients.rest_assured.helpers.ApiCompanyHelper;
import x_clients.rest_assured.helpers.AuthHelper;

@ExtendWith({CreateCompanyResponseResolver.class, ApiCompanyHelperResolver.class})
public class BusinessContractTest {

    private final static String URL = "https://x-clients-be.onrender.com/";

    private static AuthHelper authHelper;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = URL;
        authHelper = new AuthHelper();
        String userToken = authHelper.authAndGetToken("leonardo", "leads");
        RestAssured.requestSpecification = new RequestSpecBuilder().build().header("x-client-token", userToken);
    }

    @Test
    public void deleteCompany(ApiCompanyHelper apiCompanyHelper, CreateCompanyResponse createCompanyResponse)
        throws InterruptedException {
        apiCompanyHelper.deleteCompany(createCompanyResponse.id());
        Thread.sleep(3000);
        Optional<Company> company = apiCompanyHelper.getCompany(createCompanyResponse.id());
        assertTrue(company.isEmpty());
        // Проверить через GET, что компании больше нет
    }

    public void deleteNotExistentCompany(ApiCompanyHelper apiCompanyHelper) throws InterruptedException {
        apiCompanyHelper.deleteCompany(666666);
        Thread.sleep(3000);
        Optional<Company> company = apiCompanyHelper.getCompany(666666);
        System.out.println(company);
        // Проверить через GET, что компании больше нет
    }
}
