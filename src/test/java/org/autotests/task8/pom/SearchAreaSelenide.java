package org.autotests.task8.pom;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SearchAreaSelenide {

    private final SelenideElement departurePointInputField = $("input[placeholder='Откуда']");
    private final SelenideElement suggestedDeparture =
            $x("//div[@class='dp-20s1up-root-suggestionName' and normalize-space(text())='Москва']");
    private final SelenideElement destinationPointInputField = $("input[placeholder='Куда']");
    private final SelenideElement suggestedDestination =
            $x("//div[@class='dp-20s1up-root-suggestionName' " +
                    "and normalize-space(text())='Санкт-Петербург']");
    private final SelenideElement dateOfDepartureThereInputField = $("input[placeholder='Туда']");
    private final SelenideElement dateOfDepartureBackInputField = $("input[placeholder='Обратно']");
    private final SelenideElement ticketSearchButton =
            $x("//button[@type='submit']//span[text()='Поиск']");
    private final SelenideElement borderLine = $(".dp-1dr6zbu-root[data-failed]");

    @Step("Убедиться, что страница открылась")
    public void stepCheckTicketSearchAreaIsDisplayed() {
        departurePointInputField.shouldBe(visible);
        destinationPointInputField.shouldBe(visible);
        dateOfDepartureThereInputField.shouldBe(visible);
        dateOfDepartureBackInputField.shouldBe(visible);
    }

    @Step("Выбрать (или ввести) в поле Откуда: {departurePoint}")
    private void pickDeparturePoint(String departurePoint) {
        departurePointInputField.click();
        departurePointInputField.setValue(departurePoint);
        suggestedDeparture.shouldBe(visible).click();
    }

    @Step("Выбрать (или ввести) в поле Куда: {destinationPoint}")
    private void pickDestinationPoint(String destinationPoint) {
        destinationPointInputField.click();
        destinationPointInputField.setValue(destinationPoint);
        suggestedDestination.shouldBe(visible).click();
    }

    @Step("Нажать на кнопку Поиск")
    private void clickSearchButton() {
        ticketSearchButton.shouldBe(visible).click();
    }

    @Step("Выбрать (или ввести) следующие критерии поиска: {departurePoint}, {destinationPoint} и нажать на Поиск")
    public void stepSearchForFlight(String departurePoint, String destinationPoint) {
        pickDeparturePoint(departurePoint);
        pickDestinationPoint(destinationPoint);
        clickSearchButton();
    }

    @Step("Убедиться, что около поля «Туда» появилась красная обводка")
    public void stepCheckRedBorderAroundDateOfDepartureThereInputField(String expectedRedColor) {
        borderLine.shouldHave(cssValue("border-color", expectedRedColor));
        Assertions.assertEquals(expectedRedColor, borderLine.getCssValue("border-color"));
    }
}
