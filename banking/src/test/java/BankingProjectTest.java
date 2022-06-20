import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankingProjectTest {

    private static WebDriver driver;
    private static final String URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    private static final String CHROME_DRIVER_PATH = "C:/_soft/chromedriver_win32_102/chromedriver.exe";
    private static final String FIRST_NAME = "Alexey";
    private static final String LAST_NAME = "Mokin";
    private static final String POST_CODE = "123456";

    static {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
    }

    // Кейс #1 (Создать клиента)
    @Test
    @Order(1)
    public void shouldSuccessfullyAddCustomer() {
        try {
            driver.get(URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(1));

            // Click "Bank Manager Login" button
            wait.until(elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button"))).click();

            // Click "Add Customer" button
            wait.until(elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]"))).click();

            // Provide First name
            wait.until(elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input"))).sendKeys(FIRST_NAME);

            // Provide Last name
            driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input")).sendKeys(LAST_NAME);

            // Provide Post Code
            driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input")).sendKeys(POST_CODE);

            // Click Add Customer
            driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();

            // Ensure that customer was created
            Alert alert = driver.switchTo().alert();
            String alertMsg = alert.getText();
            alert.accept();
            assertTrue(alertMsg.matches("^Customer added successfully with customer id :[0-9]+$"));
        } catch (Exception e){
            driver.quit();
        }
    }

    // Кейс #2 (Клиенту, которого мы подготовили в кейсе #1 открыть счета во всех представленных валютах)
    @Test
    @Order(2)
    public void shouldSuccessfullyOpenAccount() {
        try {
            driver.get(URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(1));

            // Click "Bank Manager Login" button
            wait.until(elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button"))).click();

            // Click "Open Account" button
            wait.until(elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[2]"))).click();

            Select userSelect = new Select(wait.until(elementToBeClickable(By.id("userSelect"))));
            Select currencySelect = new Select(wait.until(elementToBeClickable(By.id("currency"))));
            List<String> currencies = Arrays.asList("Dollar", "Pound", "Rupee");

            for (String currency : currencies) {
                // Select User
                userSelect.selectByVisibleText(FIRST_NAME + " " + LAST_NAME);
                // Select Currency
                currencySelect.selectByVisibleText(currency);

                // Click Process
                driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();

                // Ensure that currency was created
                Alert alert = driver.switchTo().alert();
                String alertMsg = alert.getText();
                alert.accept();
                assertTrue(alertMsg.matches("^Account created successfully with account Number :[0-9]+$"));
            }

        } finally {
            driver.quit();
        }
    }
}
