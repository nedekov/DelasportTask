package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import helpers.ApiHelper;
import helpers.WebDriverHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;  // Correct import for JUnit 5

public class LoginSteps {
    WebDriver driver = WebDriverHelper.getDriver();  // Ensure WebDriver is initialized correctly
    String uiBalance;

    @Given("the user navigates to the login page")
    public void navigateToLoginPage() {
        driver.get("https://luckybandit.club.test-delasport.com/en/sports");
    }

    @When("the user logs in with valid credentials")
    public void loginWithValidCredentials() {
        driver.findElement(By.cssSelector(".cl-login-button")).click();
        driver.findElement(By.id("login_form[username]")).sendKeys("tu_mihail");
        driver.findElement(By.id("login-modal-password-input")).sendKeys("Pass112#");
        driver.findElement(By.cssSelector(".modal-action-bar .modal-submit-button")).click();
    }

    @Then("the user closes any modal pop-ups if present")

    public void closeModalsIfPresent() {

        try {
            // Explicit wait for the Close button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='sportsbookModal']//button[@aria-label='Close']")));

            // If the element is found and clickable, click it
            if (closeButton.isDisplayed() && closeButton.isEnabled()) {
                closeButton.click();
            }
        } catch (TimeoutException e) {
            // Handle the case where the element is not found within the timeout
            // You might want to log this or take other actions as needed
            System.out.println("Close button not found or not clickable.");
        }
    }

    @And("the user checks the balance in the header")
    public void getBalanceFromUI() throws InterruptedException {
        uiBalance = driver.findElement(By.cssSelector("span.user-balance-item-amount")).getText();

     }

    @Then("the user verifies the balance matches the API balance")
    public void verifyBalance() {

        String apiBalance = ApiHelper.getMemberBalance();
        String balanceWithoutEuro = uiBalance.replace("€", "");;
        assertEquals( balanceWithoutEuro, apiBalance,"Balance does not match!");  // Using JUnit 5 assertion
    }
}
