package org.ibs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRunner extends BaseTests {

    @Test
    void startTest() {
        System.out.println("@Test -> startTest()");
        FoodPage foodPage = FoodPage.getFoodPage();

        System.out.println("Тест-кейс: Добавление овоща, не экзотического");
        foodPage.addProducts("Перец", "Овощ", false);
        Assertions.assertTrue((foodPage.containsProduct("Перец")), "Не удалось добавить продукт: Перец, Овощ, Неэкзотический");

        System.out.println("Тест-кейс: Добавление овоща, экзотического");
        foodPage.addProducts("Батат", "Овощ", true);
        Assertions.assertTrue((foodPage.containsProduct("Батат")), "Не удалось добавить продукт: Батат, Овощ, Ээкзотический");

        System.out.println("Тест-кейс: Добавление фрукта, не экзотического");
        foodPage.addProducts("Груша", "Фрукт", false);
        Assertions.assertTrue((foodPage.containsProduct("Груша")), "Не удалось добавить продукт: Груша, Фрукт, Неэкзотический");

        System.out.println("Тест-кейс: Добавление фрукта, экзотического");
        foodPage.addProducts("Ананас", "Фрукт", true);
        Assertions.assertTrue((foodPage.containsProduct("Ананас")), "Не удалось добавить продукт: Ананас, Фрукт, Экзотический");
        System.out.println("Очистить данные");
        foodPage.clearData();
        Assertions.assertTrue((foodPage.checkTable()), "Не удалось очистить данные");
    }
}



