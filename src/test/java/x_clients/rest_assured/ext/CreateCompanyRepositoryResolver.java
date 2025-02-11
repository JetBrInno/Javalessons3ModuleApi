package x_clients.rest_assured.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    private static final String connectionString = "jdbc:postgresql://dpg-cu3n5bt2ng1s73cbrv80-a.frankfurt-postgres.render.com/x_clients_db_heq8";
    private static final String login = "x_clients_client";
    private static final String password = "B9KBls6DlHN7shcDKUcGuFWvZoQYBjU4";

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
        try {
            connection = DriverManager.getConnection(connectionString, login, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
