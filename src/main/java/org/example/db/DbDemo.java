package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbDemo {
    public static void main(String[] args) throws SQLException {
        // ПОДКЛЮЧЕНИЕ К БД
        String connectionString = "jdbc:postgresql://dpg-cu3n5bt2ng1s73cbrv80-a.frankfurt-postgres.render.com/x_clients_db_heq8";
        String login = "x_clients_client";
        String password = "B9KBls6DlHN7shcDKUcGuFWvZoQYBjU4";
        Connection connection = DriverManager.getConnection(connectionString, login, password);

        // ЗАПРОС НА ПОЛУЧЕНИЕ ДАННЫХ (три колонки)
        String SELECT_ID_NAME = "select id, name, is_active from company;";
        ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ID_NAME);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("name"));
            System.out.println(resultSet.getBoolean("is_active"));
        }
        System.out.println("Конец");

        // ЗАПРОС НА ПОЛУЧЕНИЕ КОЛИЧЕСТВА ЗАПИСЕЙ В ТАБЛИЦЕ
        String SELECT_COUNT = "select count(*) from company;";
        resultSet = connection.createStatement().executeQuery(SELECT_COUNT);
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("count"));
        }

        // ПОЛУЧЕНИЕ ЭЛЕМЕНТА ПО АЙДИ
        int id = 666;
        ResultSet resultSet1 = getCompanyInfoById(connection, id);
        while (resultSet1.next()) {
            System.out.println("Элемент по id = " + id);
            System.out.println(resultSet1.getInt("id"));
            System.out.println(resultSet1.getString("name"));
            System.out.println(resultSet1.getBoolean("is_active"));
        }
    }

    private static ResultSet getCompanyInfoById(Connection connection, int id) throws SQLException {
        String GET_COMPANY = "select * from company where id = " + id;

        return connection.createStatement().executeQuery(GET_COMPANY);
    }
}
