package org.autotests.task7.tests;

import lombok.SneakyThrows;
import org.autotests.task7.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@DisplayName("Pobeda Tests")
public class MorePobedaTests {

    /*
    Написать три автотеста, связанных с функционалом сайта «pobeda.aero».
    При разработке автотестов необходимо использовать паттерны Page Object Model и Page Factory,
    а также расставить явные и неявные ожидания там, где они нужны.

    В тестовом файле есть 3 тестовых метода, один Before метод и один After метод. За выполнение этого пункта – 1 балл.
     */

    private WebDriver driver;
    private WebDriverWait wait;

    private MainPage mainPage;
    private InformationPopup informationPopup;
    private SearchArea searchArea;
    private ManageBookingArea manageBookingArea;
    private ViewOrderPage viewOrderPage;

    private static final String URL = "https://pobeda.aero/";
    private static final String EXPECTED_MAIN_PAGE_TITLE_TEXT =
            "Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, " +
                    "прямые и трансферные рейсы с пересадками";
    private static final String EXPECTED_POPUP_PREPARING_TITLE_TEXT = "Подготовка к полёту";
    private static final String EXPECTED_POPUP_USEFUL_TITLE_TEXT = "Полезная информация";
    private static final String EXPECTED_POPUP_ABOUT_COMPANY_TITLE_TEXT = "О компании";
    private static final String EXPECTED_ORDER_ERROR_TEXT = "Заказ с указанными параметрами не найден";
    private static final String FROM = "Москва";
    private static final String TO = "Санкт-Петербург";
    private static final String EXPECTED_RED_COLOR_IN_RGB = "rgb(213, 0, 98)";

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/ayyldyrym/Downloads/chromedriver-win64/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get(URL);

        wait = new WebDriverWait(driver,Duration.ofSeconds(180));
    }

    @DisplayName("Задание №1. Page Object. Всплывающее окно")
    @Test
    public void pobedaInformationPopupTest() {
        mainPage = new MainPage(driver);
        informationPopup = new InformationPopup(driver);

        mainPage.stepCheckThePageIsDisplayed(EXPECTED_MAIN_PAGE_TITLE_TEXT);
        mainPage.hoverOverInformationElement();
        informationPopup.stepCheckInformationPopupModalIsDisplayed(EXPECTED_POPUP_PREPARING_TITLE_TEXT,
                EXPECTED_POPUP_USEFUL_TITLE_TEXT, EXPECTED_POPUP_ABOUT_COMPANY_TITLE_TEXT);
    }

    @DisplayName("Задание №2. Page Object. Инициирование поиска")
    @Test
    public void pobedaSearchForFlightTest() {
        mainPage = new MainPage(driver);
        searchArea = new SearchArea(driver);

        mainPage.stepCheckThePageIsDisplayed(EXPECTED_MAIN_PAGE_TITLE_TEXT);
        mainPage.scrollToSearchArea();  // на новом сайте Победы как будто не актуально, но добавила, раз в задании есть
        searchArea.stepCheckTicketSearchAreaIsDisplayed();
        searchArea.stepSearchForFlight(FROM, TO);
        searchArea.stepCheckRedBorderAroundDateOfDepartureThereInputField(EXPECTED_RED_COLOR_IN_RGB);
    }

    @Test
    public void pobedaSearchForTicketOrderTest() {
        mainPage = new MainPage(driver);
        manageBookingArea = new ManageBookingArea(driver);

        mainPage.stepCheckThePageIsDisplayed(EXPECTED_MAIN_PAGE_TITLE_TEXT);
        mainPage.goToManageBookingPage();
        manageBookingArea.stepCheckThePageIsDisplayed();
        manageBookingArea.stepSearchForTicketOrder("XXXXXX", "Qwerty");
        //viewOrderTab.confirmDataInTheNewTab();
        //Assertions.assertEquals(EXPECTED_ORDER_ERROR_TEXT, viewOrderTab.getErrorMessage());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
