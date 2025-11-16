package org.autotests.task7.pom;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    WebDriver driver;

    @FindBy(css = "img[src='/_next/static/media/logo-rus-white.b9d69d0a.svg']")
    WebElement logo;

    @FindBy(css = "a[href='/information']")
    WebElement informationElement;

    @FindBy(css = ".dp-1e3lq48-root-card")
    WebElement ticketSearchArea;

    @FindBy(xpath = "//button[.//span[contains(text(), 'Управление бронированием')]]")
    WebElement manageBookingButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void stepCheckThePageIsDisplayed(String expectedTitleText) {
        Assertions.assertEquals(expectedTitleText, driver.getTitle());
        Assertions.assertTrue(logo.isDisplayed());
    }

    public void hoverOverInformationElement() {
        Actions actions = new Actions(driver);
        actions.moveToElement(informationElement).perform();
    }

    public void goToManageBookingPage() {
        manageBookingButton.click();
    }

    public void scrollToSearchArea() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", ticketSearchArea);
    }
}
