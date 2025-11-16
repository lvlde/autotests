package org.autotests.task8.pom;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ManageBookingAreaSelenide {

    private final SelenideElement orderOrTicketNumberField =
            $("input[placeholder='Номер бронирования']"); //сломанный селектор
    private final SelenideElement clientLastnameField =
            $("input[placeholder='Фамилия клиента']");
    private final SelenideElement searchButton =
            $x("//button[contains(@class, 'submitBtn') and .//span[contains(text(), 'Поиск')]]");

    @Step("Убедиться, что страница открылась")
    public void stepCheckThePageIsDisplayed() {
        orderOrTicketNumberField.shouldBe(visible);
        clientLastnameField.shouldBe(visible);
        searchButton.shouldBe(visible);
    }

    @Step("Ввести номер заказа или билета")
    private void enterOrderOrTicketNumber(String orderOrTicketNumber) {
        orderOrTicketNumberField.setValue(orderOrTicketNumber);
    }

    @Step("Ввести фамилию")
    private void enterLastname(String lastname) {
        clientLastnameField.setValue(lastname);
    }

    @Step("Нажать на кнопку Поиск")
    private void clickSearchButton() {
        searchButton.click();
    }

    @Step("Ввести данные: номер заказа/билета {orderOrTicketNumber}, фамилия {lastname} и нажать на Поиск")
    public void stepSearchForTicketOrder(String orderOrTicketNumber, String lastname) {
        enterLastname(lastname);
        enterOrderOrTicketNumber(orderOrTicketNumber);
        clickSearchButton();
    }
}
