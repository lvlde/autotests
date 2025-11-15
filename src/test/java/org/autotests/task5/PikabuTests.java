package org.autotests.task5;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@DisplayName("Pikabu Tests")
public class PikabuTests {

    private static final String URL = "https://pikabu.ru/";
    private static final String EXPECTED_TITLE = "Горячее – самые интересные и обсуждаемые посты | Пикабу";
    private static final String ENTER_BUTTON_SELECTOR = "[class=\"pkb-normal-btn header-right-menu__login-button\"]";
    private static final String AUTHORIZATION_MODAL_SELECTOR = "[class=\"auth-modal\"]";
    private static final String LOGIN_FIELD_SELECTOR = "[class=\"auth-modal\"] [name=\"username\"]";
    private static final String PASSWORD_FIELD_SELECTOR = "[class=\"auth-modal\"] [name=\"password\"]";
    private static final String SUBMIT_BUTTON_SELECTOR = "[class=\"auth-modal\"] [class=\"button__title\"]";
    private static final String ERROR_MESSAGE_SELECTOR =
            "[class=\"popup__content\"] [class=\"auth__error auth__error_top\"]";
    private static final String LOGIN_KEYS_TO_SEND = "Qwerty";
    private static final String PASSWORD_KEYS_TO_SEND = "Qwerty";
    private static final String EXPECTED_ERROR_TEXT = "Ошибка. Вы ввели неверные данные авторизации";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/ayyldyrym/Downloads/chromedriver-win64/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        driver.manage().window().maximize();

        driver.get(URL);

        wait = new WebDriverWait(driver,Duration.ofSeconds(30));
    }

    @Test
    public void pikabuTest() {
        Assertions.assertEquals(URL, driver.getCurrentUrl());
        Assertions.assertEquals(EXPECTED_TITLE, driver.getTitle());

        driver.findElement(By.cssSelector(ENTER_BUTTON_SELECTOR)).click();

        Assertions.assertTrue(driver.findElement(By.cssSelector(AUTHORIZATION_MODAL_SELECTOR)).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(LOGIN_FIELD_SELECTOR)).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(PASSWORD_FIELD_SELECTOR)).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).isDisplayed());

        driver.findElement(By.cssSelector(LOGIN_FIELD_SELECTOR)).click();
        driver.findElement(By.cssSelector(LOGIN_FIELD_SELECTOR)).sendKeys(LOGIN_KEYS_TO_SEND);
        driver.findElement(By.cssSelector(LOGIN_FIELD_SELECTOR)).sendKeys(Keys.ENTER);

        driver.findElement(By.cssSelector(PASSWORD_FIELD_SELECTOR)).click();
        driver.findElement(By.cssSelector(PASSWORD_FIELD_SELECTOR)).sendKeys(PASSWORD_KEYS_TO_SEND);
        driver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ERROR_MESSAGE_SELECTOR)));
        Assertions.assertTrue(driver.findElement(By.cssSelector(ERROR_MESSAGE_SELECTOR)).isDisplayed());
        Assertions.assertEquals(EXPECTED_ERROR_TEXT,
                driver.findElement(By.cssSelector(ERROR_MESSAGE_SELECTOR)).getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
