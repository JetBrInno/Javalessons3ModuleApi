package x_clients.rest_assured.ext;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import x_clients.rest_assured.db.CompanyRepository;
import x_clients.rest_assured.db.CompanyRepositoryJDBC;

public class CreateCompanyRepositoryResolver implements ParameterResolver, BeforeAllCallback, AfterAllCallback {

    private Connection connection;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CompanyRepository.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
        throws ParameterResolutionException {
        return new CompanyRepositoryJDBC(connection);
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "env.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));

        String connectionString = properties.getProperty("db.connectionString");
        String login = properties.getProperty("db.login");;
        String password = properties.getProperty("db.password");;

        connection = DriverManager.getConnection(connectionString, login, password);
    }
}
