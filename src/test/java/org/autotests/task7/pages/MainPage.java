package org.autotests.task7.pages;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MainPage {
    /*
    В каждом файле с POM у каждого WebElement должна быть аннотация @FindBy и функция с методом initElements().
    За выполнение этого пункта – 1 балл. Если хотя бы в одном файле такого нет – 0 баллов.

========
2. Убедиться, что сайт открылся:
б) на странице есть логотип Победы.
3. Навести мышку на пункт «Информация».
     */

    WebDriver driver;

    By logo = By.cssSelector("img[src='/_next/static/media/logo-rus-white.b9d69d0a.svg']");
    By informationElement = By.cssSelector("a[href='/information']");
    By ticketSearchAreaSelector = By.cssSelector(".dp-1e3lq48-root-card");

    //TODO пункт «Управление бронированием»:
    private By manageBookingButtonSelector =
            By.xpath("//button[.//span[contains(text(), 'Управление бронированием')]]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void stepCheckThePageIsDisplayed(String expectedTitleText) {
        Assertions.assertEquals(expectedTitleText, driver.getTitle());
        Assertions.assertTrue(getLogo().isDisplayed());
    }

    public WebElement getLogo() {
        return driver.findElement(logo);
    }

    public void hoverOverInformationElement() {
        WebElement element = driver.findElement(informationElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void goToManageBookingPage() {
        driver.findElement(manageBookingButtonSelector).click();
    }

    public void scrollToSearchArea() {
        WebElement searchArea = driver.findElement(ticketSearchAreaSelector);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", searchArea);
    }
}
