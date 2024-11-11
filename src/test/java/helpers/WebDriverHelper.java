package helpers;

import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverHelper {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            // I had issues with the chromedriver version, so I pointed the path to it
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\ailqk\\.cache\\selenium\\chromedriver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }
    @After
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
