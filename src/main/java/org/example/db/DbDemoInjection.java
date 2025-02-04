package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Этот класс показывает, как НЕЛЬЗЯ писать код, чтобы не допустить SQL инъекций
 */
public class DbDemoInjection {
    public static void main(String[] args) throws SQLException {


        // ПОДКЛЮЧЕНИЕ К БД
        String connectionString = "jdbc:postgresql://dpg-cu3n5bt2ng1s73cbrv80-a.frankfurt-postgres.render.com/x_clients_db_heq8";
        String login = "x_clients_client";
        String password = "B9KBls6DlHN7shcDKUcGuFWvZoQYBjU4";
        Connection connection = DriverManager.getConnection(connectionString, login, password);

        // ПОЛУЧЕНИЕ ЭЛЕМЕНТА ПО ИМЕНИ
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя:");
        String name = scanner.nextLine();

        ResultSet resultSet1 = getCompanyInfoByName(connection, name);
        while (resultSet1.next()) {
            System.out.println("Элемент по имени: " + name);
            System.out.println(resultSet1.getInt("id"));
            System.out.println(resultSet1.getString("name"));
            System.out.println(resultSet1.getBoolean("is_active"));
        }
    }

    private static ResultSet getCompanyInfoByName(Connection connection, String name) throws SQLException {
        String GET_COMPANY = "select * from company where name = '" + name + "'";

        return connection.createStatement().executeQuery(GET_COMPANY);
    }
}
