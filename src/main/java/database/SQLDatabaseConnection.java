/*
 * Name     :   SQLDatabaseConnection.java
 * Author   :   joeca
 * Date     :   08 Mar 2021
 */
package database;

import java.sql.*;
import java.util.UUID;

public class SQLDatabaseConnection {

    private static final String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=db_codebase;integratedSecurity=true;";

    public static void queryDatabase(String query) {
        ResultSet resultSet = null;
        try(Connection connection = DriverManager.getConnection(connectionString); Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " +resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SQLDatabaseConnection.queryDatabase("SELECT * FROM user_table");
    }

}
