package org.autotests.task7.pom;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManageBookingArea {

    @FindBy(css = "input[placeholder='Номер бронирования или билета']")
    WebElement orderOrTicketNumberField;

    @FindBy(css = "input[placeholder='Фамилия клиента']")
    WebElement clientLastnameField;

    @FindBy(xpath = "//button[.//span[contains(text(), 'Поиск')]]")
    WebElement searchButton;

    By orderOrTicketNumberFieldSelector = By.cssSelector("input[placeholder='Номер бронирования или билета']");

    WebDriver driver;

    public ManageBookingArea(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void stepCheckThePageIsDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderOrTicketNumberFieldSelector));
        Assertions.assertTrue(orderOrTicketNumberField.isDisplayed());
        Assertions.assertTrue(clientLastnameField.isDisplayed());
        Assertions.assertTrue(searchButton.isDisplayed());
    }

    private void enterOrderOrTicketNumber(String orderOrTicketNumber) {
        orderOrTicketNumberField.click();
        orderOrTicketNumberField.sendKeys(orderOrTicketNumber);
    }

    private void enterLastname(String lastname) {
        clientLastnameField.click();
        clientLastnameField.sendKeys(lastname);
    }

    private void clickSearchButton() {
        searchButton.click();
    }

    public void stepSearchForTicketOrder(String orderOrTicketNumber, String lastname) {
        enterLastname(lastname);
        enterOrderOrTicketNumber(orderOrTicketNumber);

        //String originalWindow = driver.getWindowHandle();
        //Set<String> oldTabs = driver.getWindowHandles();

        //TODO fix
        clickSearchButton();

        //new WebDriverWait(driver, Duration.ofSeconds(100))
        //        .until(driver1 -> driver1.getWindowHandles().size() > oldTabs.size());

        //for (String handle : driver.getWindowHandles()) {
        //    if (oldTabs.contains(handle)) {
        //        driver.switchTo().window(handle);
        //        break;
        //    }
        //}

        //new WebDriverWait(driver, Duration.ofSeconds(10))
        //        .until(web -> ((JavascriptExecutor) web)
        //                .executeScript("return document.readyState").toString().equals("complete"));
    }
}
