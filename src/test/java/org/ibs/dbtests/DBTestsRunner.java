package org.ibs.dbtests;

import org.ibs.dbclasses.Food;
import org.ibs.managers.DBManager;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

public class DBTestsRunner {

    @Test
    public void registerDriver() throws ClassNotFoundException {
        DriverManager.drivers().map(m -> m.getClass().getName()).forEach(System.out::println);

    }

    @Test
    public void DBTestsRunner() throws SQLException {
        DBManager.getDBManager();
        DBManager dbManager = DBManager.getDBManager();

        List<Food> foodList = List.of(
                new Food("Перец", "VEGETABLE", false),
                new Food("Батат", "VEGETABLE", true),
                new Food("Груша", "FRUIT", false),
                new Food("Ананас", "FRUIT", true),
                new Food("Яблоко", "FRUIT", false)
        );

        dbManager.insertData(foodList);
        System.out.println("Найдены: ");
        dbManager.isDataInsert("SELECT * FROM FOOD WHERE FOOD_NAME IN ('Перец', 'Груша', 'Ананас', 'Батат')");


        dbManager.clearData("DELETE FROM FOOD WHERE FOOD_NAME IN ('Перец', 'Груша', 'Ананас', 'Батат')");
        boolean isDelete = dbManager.isDataDelete("SELECT * FROM FOOD WHERE FOOD_NAME IN ('Перец', 'Груша', 'Ананас', 'Батат')");
        if (isDelete) {
            System.out.println("Данные успешно удалены из базы данных.");
        } else {
            System.out.println("Ошибка при удалении данных из базы данных.");
        }
        DBManager.getDBManager().closeConnection();

    }
}
