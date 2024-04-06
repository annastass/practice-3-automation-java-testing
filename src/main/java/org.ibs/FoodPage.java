package org.ibs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class FoodPage {

    private final WebDriver webDriver = DriverManager.getDriver();
    private final TestPropManager props = TestPropManager.getTestPropManager();

    private static FoodPage foodPage;

    public static FoodPage getFoodPage() {
        if (foodPage == null) {
            foodPage = new FoodPage();
        }
        return foodPage;
    }

    private By addProductsButtonLocator = By.xpath("//button[@data-toggle='modal']");
    private By saveProductsButtonLocator = By.id("save");
    private By nameFieldLocator = By.id("name");
    private By typeDropdownLocator = By.id("type");
    private By exoticCheckboxLocator = By.id("exotic");
    private By navSandboxLocator = By.id("navbarDropdown");
    private By resetDataLocator = By.id("reset");
    private By rowsLocator = By.xpath("//tbody/tr");

    public void addProducts(String name, String type, boolean exotic) {
        webDriver.get(props.getProperty(PropConst.BASE_URL));

        WebElement btnAddProducts = webDriver.findElement(addProductsButtonLocator);
        btnAddProducts.click();

        insertData(name, type, exotic);

        WebElement btnSaveProducts = webDriver.findElement(saveProductsButtonLocator);
        btnSaveProducts.click();
    }

    private void insertData(String name, String type, boolean exotic) {
        WebElement nameField = webDriver.findElement(nameFieldLocator);
        nameField.sendKeys(name);

        Select typeDropdown = new Select(webDriver.findElement(typeDropdownLocator));
        typeDropdown.selectByVisibleText(type);

        WebElement exoticCheckbox = webDriver.findElement(exoticCheckboxLocator);
        if (exotic) {
            exoticCheckbox.click();
        }
    }

    public void clearData() {
        WebElement navSandbox = webDriver.findElement(navSandboxLocator);
        navSandbox.click();
        WebElement resetData = webDriver.findElement(resetDataLocator);
        resetData.click();
    }

    public boolean containsProduct(String name) {
        List<WebElement> rows = webDriver.findElements(rowsLocator);

        for (WebElement row : rows) {
            WebElement productCell = row.findElement(By.xpath(".//td[1]"));
            String productText = productCell.getText();

            if (productText.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTable() {
        List<WebElement> rows = webDriver.findElements(rowsLocator);
        return rows.size() == 4;
    }
}
