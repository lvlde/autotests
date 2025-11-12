package org.autotests.task5;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@DisplayName("Pikabu Tests")
public class PikabuTests {

    String url = "https://pikabu.ru/";
    String expectedTitle = "Горячее – самые интересные и обсуждаемые посты | Пикабу";
    String headerCloserButtonSelector = "data-testid=\"suggest:header:closer\"";
    //<button class="eBPk20VGZ" data-t="suggest:header:closer" ><svg class="eoxtTP+dB" width="24" height="24" viewBox="0 0 24 24" fill="#262633" xmlns="http://www.w3.org/2000/svg"><path d="M19.2071 6.20711C19.5976 5.81658 19.5976 5.18342 19.2071 4.79289C18.8166 4.40237 18.1834 4.40237 17.7929 4.79289L12 10.5858L6.20711 4.79289C5.81658 4.40237 5.18342 4.40237 4.79289 4.79289C4.40237 5.18342 4.40237 5.81658 4.79289 6.20711L10.5858 12L4.79289 17.7929C4.40237 18.1834 4.40237 18.8166 4.79289 19.2071C5.18342 19.5976 5.81658 19.5976 6.20711 19.2071L12 13.4142L17.7929 19.2071C18.1834 19.5976 18.8166 19.5976 19.2071 19.2071C19.5976 18.8166 19.5976 18.1834 19.2071 17.7929L13.4142 12L19.2071 6.20711Z"></path></svg></button>
    String enterButtonSelector = "[class=\"pkb-normal-btn\"]";
    String authorizationModalSelector = "[class=\"auth-modal\"]";
    String loginFieldSelector = "[class=\"auth-modal\"] [name=\"username\"]";
    String passwordFieldSelector = "[class=\"auth-modal\"] [name=\"password\"]";
    String submitButtonSelector = "[class=\"auth-modal\"] [class=\"button__title\"]";
    String errorMessageSelector = "[class=\"popup__content\"] [class=\"auth__error auth__error_top\"]";
    String expectedErrorText = "Ошибка. Вы ввели неверные данные авторизации";

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ayyldyrym/Downloads/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
    }

    @Test
    public void pikabuTest() {


        Assertions.assertEquals(url, driver.getCurrentUrl());
        Assertions.assertEquals(expectedTitle, driver.getTitle());

        //WebElement headerCloserButton = new WebDriverWait(driver, Duration.ofSeconds(90))
        //        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(headerCloserButtonSelector)));
        //headerCloserButton.click();
        //driver.findElement(By.cssSelector(headerCloserButton)).click();

        //TODO Кликнуть на кнопку «Войти».
        //Assertions.assertTrue(driver.findElement(By.cssSelector(enterButtonSelector)).isDisplayed());
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", enterButtonSelector);

        //WebElement enterButton = new WebDriverWait(driver, Duration.ofSeconds(10))
         //       .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(enterButtonSelector)));
        //enterButton.click();



        //TODO Убедиться, что отображается модальное окно «Авторизация»
        Assertions.assertTrue(driver.findElement(By.cssSelector(authorizationModalSelector)).isDisplayed());

        //TODO Убедиться, что отображаются поля «Логин» и «Пароль»
        Assertions.assertTrue(driver.findElement(By.cssSelector(loginFieldSelector)).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(passwordFieldSelector)).isDisplayed());
        //TODO Убедиться, что отображается кнопка «Войти»
        Assertions.assertTrue(driver.findElement(By.cssSelector(authorizationModalSelector)).isDisplayed());
        //TODO Ввести в поля данные в формате логин/пароль – Qwerty/Qwerty и нажать «Войти».
        driver.findElement(By.cssSelector(loginFieldSelector)).click();
        driver.findElement(By.cssSelector(loginFieldSelector)).sendKeys("Qwerty");
        driver.findElement(By.cssSelector(loginFieldSelector)).sendKeys(Keys.ENTER);

        driver.findElement(By.cssSelector(passwordFieldSelector)).click();
        driver.findElement(By.cssSelector(passwordFieldSelector)).sendKeys("Qwerty");
        driver.findElement(By.cssSelector(submitButtonSelector)).click();

        //TODO Убедиться, что появилось сообщение об ошибке, и его текст: «Ошибка. Вы ввели неверные данные авторизации».
        Assertions.assertTrue(driver.findElement(By.cssSelector(errorMessageSelector)).isDisplayed());
        Assertions.assertEquals(driver.findElement(By.cssSelector(errorMessageSelector)).getText(), expectedErrorText);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
