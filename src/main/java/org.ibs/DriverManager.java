package org.ibs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    private static DriverManager INSTANCE = null;
    private static WebDriver driver;
    private static final TestPropManager prop = TestPropManager.getTestPropManager();

    private DriverManager() {}

    public static DriverManager getDriverManager() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public static void initDriver(){
        String chromeDriverPath = prop.getProperty(PropConst.PATH_CHROME_DRIVER_WINDOWS);
        System.setProperty("webdriver.chrome.driver", "D:\\studies\\Java_Automation\\src\\main\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
    }


    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
