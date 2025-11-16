package org.autotests.task7.pom;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class ViewOrderPage {

    WebDriver driver;

    @FindBy(css = "div.customCheckbox label")
    WebElement checkbox;

    @FindBy(css = "button.btn_search--order")
    WebElement submitButton;

    @FindBy(css = "div.message_error")
    WebElement errorMessageElement;

    public ViewOrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private void clickCheckbox() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
    }

    private void clickSubmitButton() {
        submitButton.click();
    }

    public void confirmDataInTheNewTab(String originalHandle) {
        switchToNewTab(originalHandle);
        new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.titleIs("Просмотр заказа"));
        clickCheckbox();
        clickSubmitButton();
    }

    private void switchToNewTab(String originalHandle) {
        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                System.out.println("Switched to tab " + driver.getTitle() + " " + driver.getCurrentUrl());
                return;
            }
        }
        System.out.println("No new tab found. Staying on the original tab");
    }

    public String getErrorMessage() {
        return errorMessageElement.getText();
    }
}
