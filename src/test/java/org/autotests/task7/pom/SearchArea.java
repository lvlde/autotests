package org.autotests.task7.pom;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchArea {

    @FindBy(css = "input[placeholder='Откуда']")
    WebElement departurePointInputField;

    @FindBy(css = "//div[@class='dp-20s1up-root-suggestionName' and normalize-space(text())='Москва']")
    WebElement suggestedDeparture;

    @FindBy(css = "input[placeholder='Куда']")
    WebElement destinationPointInputField;

    @FindBy(xpath = "//div[@class='dp-20s1up-root-suggestionName' and normalize-space(text())='Санкт-Петербург']")
    WebElement suggestedDestination;

    @FindBy(css = "input[placeholder='Туда']")
    WebElement dateOfDepartureThereInputField;

    @FindBy(css = "input[placeholder='Обратно']")
    WebElement dateOfDepartureBackInputField;

    @FindBy(xpath = "//button[@type='submit']//span[text()='Поиск']")
    WebElement ticketSearchButton;

    @FindBy(css = ".dp-1dr6zbu-root[data-failed]")
    WebElement borderLine;

    WebDriver driver;

    public SearchArea(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void stepCheckTicketSearchAreaIsDisplayed() {
        Assertions.assertTrue(departurePointInputField.isDisplayed());
        Assertions.assertTrue(destinationPointInputField.isDisplayed());
        Assertions.assertTrue(dateOfDepartureThereInputField.isDisplayed());
        Assertions.assertTrue(dateOfDepartureBackInputField.isDisplayed());
    }

    private void pickDeparturePoint(String departurePoint) {
        String inputValue = departurePointInputField.getAttribute("value");
        if (inputValue == null) {
            departurePointInputField.sendKeys(departurePoint);
            suggestedDeparture.click();
        } else if (!inputValue.equals(departurePoint)) {
            departurePointInputField.clear();
            departurePointInputField.sendKeys(departurePoint);
            suggestedDeparture.click();
        }
    }

    private void pickDestinationPoint(String destinationPoint) {
        destinationPointInputField.click();
        destinationPointInputField.sendKeys(destinationPoint);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(suggestedDestination));
        suggestedDestination.click();
    }

    private void clickSearchButton() {
        ticketSearchButton.click();
    }

    public void stepSearchForFlight(String departurePoint, String destinationPoint) {
        pickDeparturePoint(departurePoint);
        pickDestinationPoint(destinationPoint);
        clickSearchButton();
    }

    public void stepCheckRedBorderAroundDateOfDepartureThereInputField(String expectedRedColor) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> borderLine.getCssValue("border-color").equals(expectedRedColor));
        Assertions.assertEquals(expectedRedColor, borderLine.getCssValue("border-color"));
    }
}
