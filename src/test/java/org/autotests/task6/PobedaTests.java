package org.autotests.task6;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@DisplayName("Pobeda Tests")
public class PobedaTests {
    private static final String URL = "https://www.google.com/";
    private static final String SEARCH_FIELD_SELECTOR = "[aria-label=\"Найти\"]";
    private static final String SEARCH_ENTRY = "Сайт компании Победа";
    private static final String FIRST_LINK_SELECTOR = "[href=\"https://www.flypobeda.ru/\"]";


    private static final String LANGUAGE_XPATH_SELECTOR = "//*[@id=\"__next\"]/div[2]/header/div/div/div[1]/div[2]/button[1]";


    private static final String EXPECTED_IMG_SELECTOR = "[srcset*=\"Kaliningrad_banner_fall_d1902e0766.jpg\"]";
    private static final String EXPECTED_TEXT_XPATH_SELECTOR = "\"//div[text()='Полетели в Калининград!']/ancestor::button//img\"";          //TODO
    //private static final String LANGUAGE_XPATH_SELECTOR = "//button/text()[. =\"РУС\"]";
    private static final String ENGLISH_XPATH_SELECTOR = "//div/text()[. =\"English\"]";
    private static final String FIRST_EXPECTED_TEXT_XPATH_SELECTOR = "//span/text()[. =\"Ticket search\"]";
    private static final String SECOND_EXPECTED_TEXT_XPATH_SELECTOR = "//span/text()[. =\"Online check-in\"]";
    private static final String THIRD_EXPECTED_TEXT_XPATH_SELECTOR = "//span/text()[. =\"Manage my booking\"]";
    private static final String FIRST_EXPECTED_TEXT = "Ticket search";
    private static final String SECOND_EXPECTED_TEXT = "Online check-in";
    private static final String THIRD_EXPECTED_TEXT = "Manage my booking";

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
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(50));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        driver.manage().window().maximize();

        driver.get(URL);

        wait = new WebDriverWait(driver,Duration.ofSeconds(180));
    }

    @Test
    public void pobedaTest() {
        Assertions.assertEquals(URL, driver.getCurrentUrl());

        driver.findElement(By.cssSelector(SEARCH_FIELD_SELECTOR)).click();
        driver.findElement(By.cssSelector(SEARCH_FIELD_SELECTOR)).sendKeys(SEARCH_ENTRY);
        driver.findElement(By.cssSelector(SEARCH_FIELD_SELECTOR)).sendKeys(Keys.ENTER);

        driver.findElement(By.cssSelector(FIRST_LINK_SELECTOR)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EXPECTED_TEXT_XPATH_SELECTOR)));
        Assertions.assertTrue(driver.findElement(By.cssSelector(EXPECTED_IMG_SELECTOR)).isDisplayed());

        driver.findElement(By.xpath(LANGUAGE_XPATH_SELECTOR)).click();
        driver.findElement(By.xpath(ENGLISH_XPATH_SELECTOR)).click();

        WebElement firstText = driver.findElement(By.xpath(FIRST_EXPECTED_TEXT_XPATH_SELECTOR));
        wait.until(ExpectedConditions.textToBePresentInElement(firstText, FIRST_EXPECTED_TEXT));
        Assertions.assertTrue(driver.findElement(By.xpath(FIRST_EXPECTED_TEXT_XPATH_SELECTOR)).isDisplayed());

        WebElement secondText = driver.findElement(By.xpath(SECOND_EXPECTED_TEXT_XPATH_SELECTOR));
        wait.until(ExpectedConditions.textToBePresentInElement(secondText, SECOND_EXPECTED_TEXT));
        Assertions.assertTrue(driver.findElement(By.xpath(SECOND_EXPECTED_TEXT_XPATH_SELECTOR)).isDisplayed());

        WebElement thirdText = driver.findElement(By.xpath(THIRD_EXPECTED_TEXT_XPATH_SELECTOR));
        wait.until(ExpectedConditions.textToBePresentInElement(thirdText, THIRD_EXPECTED_TEXT));
        Assertions.assertTrue(driver.findElement(By.xpath(THIRD_EXPECTED_TEXT_XPATH_SELECTOR)).isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
