package org.ibs.managers;

import org.ibs.dbclasses.Food;

import java.sql.*;
import java.sql.DriverManager;
import java.util.List;

import static org.ibs.utils.PropConst.*;

public class DBManager {

    private final TestPropManager prop = TestPropManager.getTestPropManager();
    private static DBManager INSTANCE = null;
    private Connection connection = getConnection();

    private DBManager() throws SQLException {
    };

    public static DBManager getDBManager() throws SQLException {
        if (INSTANCE == null) {
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }

    public void insertData(List<Food> foodList) throws SQLException {
        Connection connection = getConnection();
        String insert ="INSERT INTO FOOD (FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) VALUES (?, ?, ?)";
        String select = "SELECT * FROM FOOD WHERE FOOD_NAME = ?";
        PreparedStatement pstmtCheck = connection.prepareStatement(select);
        PreparedStatement pstmt = connection.prepareStatement(insert);
        for (Food food: foodList) {

            pstmtCheck.setString(1, food.getName());
            ResultSet resultSet = pstmtCheck.executeQuery();

            if (resultSet.next()) {
                System.out.println("Уже существует: " + food.getName());
            } else if (!resultSet.next()){
                pstmt.setString(1, food.getName());
                pstmt.setString(2, food.getType());
                pstmt.setBoolean(3, food.isExotic());
                pstmt.addBatch();
            }
        }
        pstmt.executeBatch();
    }

    public void isDataInsert(String sql) throws SQLException {
        ResultSet resultSet = executeQuery(connection, sql);
        while (resultSet.next()){
            int id = resultSet.getInt("FOOD_ID");
            String name = resultSet.getString("FOOD_NAME");
            String type = resultSet.getString("FOOD_TYPE");
            boolean exotic = resultSet.getBoolean("FOOD_EXOTIC");
            System.out.printf("%d, %s, %s, %s\n", id, name, type,exotic);
        }
        resultSet.close();
    }

    public void clearData(String sql) throws SQLException {
        executeUpdate(sql);
    }

    public boolean isDataDelete(String sql) throws SQLException {
        ResultSet resultSet = executeQuery(connection, sql);
        boolean productsDeleted = !resultSet.next();
        resultSet.close();
        return productsDeleted;
    }

    private ResultSet executeQuery(Connection connection, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    private void executeUpdate(String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(prop.getProperty(DATABASE_H2_URL),prop.getProperty(DATABASE_H2_LOGIN), prop.getProperty(DATABASE_H2_PASSWORD));
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
                connection.close();
        }
    }
}
