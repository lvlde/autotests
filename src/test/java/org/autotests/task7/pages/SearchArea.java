package org.autotests.task7.pages;

import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchArea {

    @Getter
    @FindBy(css = "input[placeholder='Откуда']")
    WebElement departurePointInputField;

    @Getter
    @FindBy(css = "input[placeholder='Куда']")
    WebElement destinationPointInputField;

    @Getter
    @FindBy(css = "input[placeholder='Туда']")
    WebElement dateOfDepartureThereInputField;

    @Getter
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
        Assertions.assertTrue(getDeparturePointInputField().isDisplayed());
        Assertions.assertTrue(getDestinationPointInputField().isDisplayed());
        Assertions.assertTrue(getDateOfDepartureThereInputField().isDisplayed());
        Assertions.assertTrue(getDateOfDepartureBackInputField().isDisplayed());
    }

    private void pickDeparturePoint(String departurePoint) {
        String inputValue = getDeparturePointInputField().getAttribute("value");
        if (inputValue == null) {
            getDeparturePointInputField().sendKeys(departurePoint);
        } else if (!inputValue.equals(departurePoint)) {
            getDeparturePointInputField().clear();
            getDeparturePointInputField().sendKeys(departurePoint);
        }
    }

    private void pickDestinationPoint(String destinationPoint) {
        getDestinationPointInputField().click();
        getDestinationPointInputField().sendKeys(destinationPoint);
    }

    private void clickSearchButton() {
        ticketSearchButton.click();
    }

    public void stepSearchForFlight(String departurePoint, String destinationPoint) {
        pickDeparturePoint(departurePoint);
        pickDestinationPoint(destinationPoint);
        clickSearchButton();
    }

    @SneakyThrows
    public void stepCheckRedBorderAroundDateOfDepartureThereInputField(String expectedRedColor) {
        Thread.sleep(3000);     //TODO fix
        String borderColor = borderLine.getCssValue("border-color");
        Assertions.assertEquals(expectedRedColor, borderColor);

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.elementSelectionStateToBe()
        //        .attributeToBe(driver.findElement(parentOfDateOfDepartureThereInputSelector),
        //                "data-failed", "true"));

        //String dataFailedValue = driver.findElement(parentOfDateOfDepartureThereInputSelector)
        //        .getAttribute("data-failed");
    }
}
