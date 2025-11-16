package org.autotests.task7.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ViewOrderPage {

    WebDriver driver;

    By checkboxSelector = By.cssSelector("input#searchOrderAgreeChb");
    By submitButton = By.cssSelector("button.btn_search--order");
    By errorMessageSelector = By.cssSelector("div.message_error");

    public ViewOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    private void clickCheckbox() {
        driver.findElement(checkboxSelector).click();
    }

    private void clickSubmitButton() {
        driver.findElement(submitButton).click();
    }

    public void confirmDataInTheNewTab() {
        clickCheckbox();
        clickSubmitButton();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessageSelector).getText();
    }
}
