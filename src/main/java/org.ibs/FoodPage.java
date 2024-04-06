package org.ibs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodPage {

    private static final WebDriver webDriver = DriverManager.getDriver();
    private final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
    private final TestPropManager props = TestPropManager.getTestPropManager();

    private static FoodPage foodPage;

    private By addProductsButtonLocator = By.xpath("//button[@data-toggle='modal']");
    private By saveProductsButtonLocator = By.id("save");
    private By nameFieldLocator = By.id("name");
    private By typeDropdownLocator = By.id("type");
    private By exoticCheckboxLocator = By.id("exotic");
    private By navSandboxLocator = By.id("navbarDropdown");
    private By resetDataLocator = By.id("reset");
    private By rowsLocator = By.xpath("//tbody/tr");

    private List<WebElement> rows;
    private Map<String, String> dataTable;

    private FoodPage() {
        rows = webDriver.findElements(rowsLocator);
        dataTable = saveTable(rows);
    }

    public static FoodPage getFoodPage() {
        if (foodPage == null) {
            foodPage = new FoodPage();
        }
        return foodPage;
    }

    private Map<String, String> saveTable(List<WebElement> rows) {
        Map<String, String> tableData = new HashMap<>();
        for (WebElement row : rows) {
            List<WebElement> productCells = row.findElements(By.tagName(".//td"));
            String rowData = "";
            if (!productCells.isEmpty()) {
                String key = productCells.get(0).getText();
                for (int i = 1; i < productCells.size(); i++) {
                    rowData += productCells.get(i).getText() + " ";
                }
                tableData.put(key, rowData);
            }
        }
        return tableData;
    }

    public void addProducts(String name, String type, boolean exotic) {
        webDriver.get(props.getProperty(PropConst.BASE_URL));
        WebElement btnAddProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(addProductsButtonLocator));
        btnAddProducts.click();
        insertData(name, type, exotic);
        WebElement btnSaveProducts = wait.until(ExpectedConditions.elementToBeClickable(saveProductsButtonLocator));
        btnSaveProducts.click();
        rows = webDriver.findElements(rowsLocator);
    }

    private void insertData(String name, String type, boolean exotic) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(nameFieldLocator));
        nameField.sendKeys(name);
        Select typeDropdown = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(typeDropdownLocator)));
        typeDropdown.selectByVisibleText(type);
        WebElement exoticCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(exoticCheckboxLocator));
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
            WebElement productCells = row.findElement(By.xpath(".//td"));
            String productText = productCells.getText();
            if (productText.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTable() {
        List<WebElement> currentRows = webDriver.findElements(rowsLocator);
        Map<String, String> currentTable = saveTable(currentRows);
        return dataTable.equals(currentTable);
    }
}
