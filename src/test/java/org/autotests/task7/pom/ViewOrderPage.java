package org.autotests.task7.pom;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewOrderPage {

    WebDriver driver;

    @Getter
    @FindBy(css = "input#searchOrderAgreeChb")
    WebElement checkbox;

    @Getter
    @FindBy(css = "button.btn_search--order")
    WebElement submitButton;

    @Getter
    @FindBy(css = "div.message_error")
    WebElement errorMessageElement;

    //By checkboxSelector = By.cssSelector("input#searchOrderAgreeChb");
    //By submitButton = By.cssSelector("button.btn_search--order");
    //By errorMessageSelector = By.cssSelector("div.message_error");

    public ViewOrderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private void clickCheckbox() {
        checkbox.click();
    }

    private void clickSubmitButton() {
        submitButton.click();
    }

    public void confirmDataInTheNewTab() {
        clickCheckbox();
        clickSubmitButton();
    }

    public String getErrorMessage() {
        return errorMessageElement.getText();
    }
}
