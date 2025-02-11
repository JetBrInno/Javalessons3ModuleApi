package x_clients.rest_assured;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import x_clients.rest_assured.db.CompanyRepository;
import x_clients.rest_assured.entity.Company;
import x_clients.rest_assured.entity.CompanyDB;
import x_clients.rest_assured.entity.CreateCompanyResponse;
import x_clients.rest_assured.ext.ApiCompanyHelperResolver;
import x_clients.rest_assured.ext.CreateCompanyResponseResolver;
import x_clients.rest_assured.ext.CreateCompanyRepositoryResolver;
import x_clients.rest_assured.helpers.ApiCompanyHelper;
import x_clients.rest_assured.helpers.AuthHelper;

@ExtendWith({CreateCompanyRepositoryResolver.class, ApiCompanyHelperResolver.class, CreateCompanyResponseResolver.class})
public class CompanyBusinessTest {

    private final static String URL = "https://x-clients-be.onrender.com/";
    private static AuthHelper authHelper;
    private static final String name = "тест с бд";
    private static final String description = "описание с бд";

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = URL;
        authHelper = new AuthHelper();
        String userToken = authHelper.authAndGetToken("leonardo", "leads");
        RestAssured.requestSpecification = new RequestSpecBuilder().build().header("x-client-token", userToken);
    }

    @Test
    @DisplayName("Удаление компании")
    public void deleteCompany(ApiCompanyHelper apiCompanyHelper, CreateCompanyResponse createCompanyResponse, CompanyRepository companyRepository)
        throws InterruptedException, SQLException {

        int companyId = createCompanyResponse.id();
        apiCompanyHelper.deleteCompany(companyId);
        Thread.sleep(2000);

        CompanyDB companyDB = companyRepository.selectById(companyId);
        assertNotNull(companyDB.deletedAt());
    }

    @Test
    @DisplayName("Удаление несуществующей компании")
    @Disabled
    public void deleteNotExistentCompany(ApiCompanyHelper apiCompanyHelper) throws InterruptedException {
        apiCompanyHelper.deleteCompany(666666);
        Thread.sleep(3000);
        Optional<Company> company = apiCompanyHelper.getCompany(666666);
        System.out.println(company);
        // Проверить через GET, что компании больше нет
    }

    @Test
    @DisplayName("Создание компании")
    public void createCompanyTest(ApiCompanyHelper apiCompanyHelper, CompanyRepository companyRepository) throws SQLException {

        CreateCompanyResponse createCompanyResponse = apiCompanyHelper.createCompany(
            name,
            description
        );
        int companyId = createCompanyResponse.id();
        CompanyDB result = companyRepository.selectById(companyId);
        assertEquals(name, result.name());
        assertEquals(description, result.description());
        assertTrue(result.isActive());
    }

    @Test
    @DisplayName("Получение информации о компании")
    public void readCompanyInfo(ApiCompanyHelper apiCompanyHelper, CompanyRepository companyRepository) throws SQLException {

        int createdId = companyRepository.createCompany(name, description);

        Optional<Company> companyResponse = apiCompanyHelper.getCompany(createdId);
        Company company = companyResponse.get();

        assertEquals(createdId, company.id());
        assertEquals(name, company.name());
        assertEquals(description, company.description());
    }
}
