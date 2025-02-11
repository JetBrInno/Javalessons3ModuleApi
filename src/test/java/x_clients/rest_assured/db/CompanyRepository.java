package x_clients.rest_assured.db;

import java.sql.SQLException;
import x_clients.rest_assured.entity.CompanyDB;

public interface CompanyRepository {

    int createCompany(String name, String description) throws SQLException;

    CompanyDB selectById(int companyId) throws SQLException;
}
