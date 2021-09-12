/*
 * Name     :   SQLDatabaseConnection.java
 * Author   :   joeca
 * Date     :   08 Mar 2021
 */
package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class SQLDatabaseConnection {

    private static final String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=codebase;" +
            "integratedSecurity=true;";

    public static void queryDatabase(String query) {
        ResultSet resultSet = null;
        ArrayList<String> results = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(connectionString); Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for(int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.println(resultSet.getString(i));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
/*        SQLDatabaseConnection.queryDatabase("INSERT INTO c_user(id, username, password, email, firstname, lastname) " +
                "values('08EC6C79-8DEC-4220-975C-6CE1EFDEA68A','test_username', 'password', 'test@gmail.com', 'test_firstname', " +
                "'test_lastname')");*/
        SQLDatabaseConnection.queryDatabase("SELECT * FROM c_user");
    }

}
