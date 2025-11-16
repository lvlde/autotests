package org.autotests.task8.tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.autotests.task8.pom.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@Epic("Тесты для сайта Победы")
@DisplayName("Pobeda Tests Selenide")
public class PobedaSelenideTests {

    private MainPageSelenide mainPage;
    private InformationPopupSelenide informationPopup;
    private SearchAreaSelenide searchArea;
    private ManageBookingAreaSelenide manageBookingArea;
    private ViewOrderPageSelenide viewOrderPage;

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
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 40000;
        Configuration.headless = false;

        open(URL);
    }

    @DisplayName("Задание №1. Page Object. Всплывающее окно - Selenide")
    @Feature("Отображение информационного всплывающего окна")
    @Description("Проверка отображения информационного всплывающего окна")
    @Test
    public void pobedaInformationPopupTest() {
        mainPage = new MainPageSelenide();
        informationPopup = new InformationPopupSelenide();

        mainPage.stepCheckThePageIsDisplayed(EXPECTED_MAIN_PAGE_TITLE_TEXT);
        mainPage.hoverOverInformationElement();
        informationPopup.stepCheckInformationPopupModalIsDisplayed(
                EXPECTED_POPUP_PREPARING_TITLE_TEXT,
                EXPECTED_POPUP_USEFUL_TITLE_TEXT,
                EXPECTED_POPUP_ABOUT_COMPANY_TITLE_TEXT);
    }

    @DisplayName("Задание №2. Page Object. Инициирование поиска - Selenide")
    @Feature("Поиск билета")
    @Description("Проверка поиска билета")
    @Test
    public void pobedaSearchForFlightTest() {
        mainPage = new MainPageSelenide();
        searchArea = new SearchAreaSelenide();

        mainPage.stepCheckThePageIsDisplayed(EXPECTED_MAIN_PAGE_TITLE_TEXT);
        mainPage.scrollToSearchArea();
        searchArea.stepCheckTicketSearchAreaIsDisplayed();
        searchArea.stepSearchForFlight(FROM, TO);
        searchArea.stepCheckRedBorderAroundDateOfDepartureThereInputField(EXPECTED_RED_COLOR_IN_RGB);
    }

    @DisplayName("Задание №3. Page Object. Результаты поиска - Selenide")
    @Feature("Поиск заказа")
    @Description("Проверка поиска заказа")
    @Issue("OMBUG-1322")
    @Test
    public void pobedaSearchForTicketOrderTest() {
        mainPage = new MainPageSelenide();
        manageBookingArea = new ManageBookingAreaSelenide();
        viewOrderPage = new ViewOrderPageSelenide();

        mainPage.stepCheckThePageIsDisplayed(EXPECTED_MAIN_PAGE_TITLE_TEXT);
        mainPage.goToManageBookingPage();
        manageBookingArea.stepCheckThePageIsDisplayed();
        manageBookingArea.stepSearchForTicketOrder("XXXXXX", "Qwerty");

        viewOrderPage.confirmDataInTheNewTab();
        Assertions.assertEquals(EXPECTED_ORDER_ERROR_TEXT, viewOrderPage.getErrorMessage());
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}
