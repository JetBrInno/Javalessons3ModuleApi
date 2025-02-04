package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Этот класс показывает, как НУЖНО писать
 */
public class DbDemoNoInjection {
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
        int id = 542;
        ResultSet resultSet1 = getCompanyInfoByNameAndId(connection, name, id);
        while (resultSet1.next()) {
            System.out.println("Элемент по имени: " + name);
            System.out.println(resultSet1.getInt("id"));
            System.out.println(resultSet1.getString("name"));
            System.out.println(resultSet1.getBoolean("is_active"));
        }
    }

    private static ResultSet getCompanyInfoByNameAndId(Connection connection, String name, int id) throws SQLException {
        String GET_COMPANY = "select * from company where name = ? and id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(GET_COMPANY);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, id);

        return preparedStatement.executeQuery();
    }
}
