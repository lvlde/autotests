package org.autotests.task8.pom;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

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

    @Step("Нажать на чекбокс")
    private void clickCheckbox() {
        checkbox.shouldBe(visible).click();
    }

    @Step("Нажать на кнопку подтверждения")
    private void clickSubmitButton() {
        submitButton.shouldBe(visible).click();
    }

    @Step("Подтвердить данные в новой вкладке")
    public void confirmDataInTheNewTab() {
        switchToNewTab();
        $("title").shouldHave(attribute("text", "Просмотр заказа"));
        clickCheckbox();
        clickSubmitButton();
    }

    @Step("Переключить фокус на новую вкладку") //я бы не использовала тут @Step, т.к. метод технический
    private void switchToNewTab() {
        List<String> tabs = getWebDriver().getWindowHandles().stream().toList();
        switchTo().window(tabs.get(tabs.size() - 1));
    }

    @Step("Получить сообщение об ошибке со страницы")
    public String getErrorMessage() {
        return errorMessageElement.shouldBe(visible).getText();
    }
}
