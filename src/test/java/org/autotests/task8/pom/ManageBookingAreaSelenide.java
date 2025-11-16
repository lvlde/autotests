package org.autotests.task8.pom;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ManageBookingAreaSelenide {

    private final SelenideElement orderOrTicketNumberField =
            $("input[placeholder='Номер бронирования или билета']");
    private final SelenideElement clientLastnameField =
            $("input[placeholder='Фамилия клиента']");
    private final SelenideElement searchButton =
            $x("//button[contains(@class, 'submitBtn') and .//span[contains(text(), 'Поиск')]]");

    public void stepCheckThePageIsDisplayed() {
        orderOrTicketNumberField.shouldBe(visible);
        clientLastnameField.shouldBe(visible);
        searchButton.shouldBe(visible);
    }

    private void enterOrderOrTicketNumber(String orderOrTicketNumber) {
        orderOrTicketNumberField.setValue(orderOrTicketNumber);
    }

    private void enterLastname(String lastname) {
        clientLastnameField.setValue(lastname);
    }

    private void clickSearchButton() {
        searchButton.click();
    }

    public void stepSearchForTicketOrder(String orderOrTicketNumber, String lastname) {
        enterLastname(lastname);
        enterOrderOrTicketNumber(orderOrTicketNumber);
        clickSearchButton();
    }
}
