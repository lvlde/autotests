package org.autotests.task8.pom;

import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ViewOrderPageSelenide {
    private final SelenideElement checkbox = $("div.customCheckbox label span");
    private final SelenideElement submitButton = $("button.btn_search--order");
    private final SelenideElement errorMessageElement = $("div.message_error");

    private void clickCheckbox() {
        checkbox.shouldBe(visible).click();
    }

    private void clickSubmitButton() {
        submitButton.shouldBe(visible).click();
    }

    public void confirmDataInTheNewTab() {
        switchToNewTab();
        $("title").shouldHave(attribute("text", "Просмотр заказа"));
        clickCheckbox();
        clickSubmitButton();
    }

    private void switchToNewTab() {
        List<String> tabs = getWebDriver().getWindowHandles().stream().toList();
        switchTo().window(tabs.get(tabs.size() - 1));
    }

    public String getErrorMessage() {
        return errorMessageElement.shouldBe(visible).getText();
    }
}
