package org.autotests.task8.pom;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPageSelenide {

    private final SelenideElement logo = $("img[src='/_next/static/media/logo-rus-white.b9d69d0a.svg']");
    private final SelenideElement informationElement = $("a[href='/information']");
    private final SelenideElement ticketSearchArea = $(".dp-1e3lq48-root-card");
    private final SelenideElement manageBookingButton =
            $x("//button[.//span[contains(text(), 'Управление бронированием')]]");

    public void stepCheckThePageIsDisplayed(String expectedTitleText) {
        Assertions.assertEquals(expectedTitleText, title());
        logo.shouldBe(visible);
    }

    public void hoverOverInformationElement() {
        informationElement.hover();
    }

    public void goToManageBookingPage() {
        manageBookingButton.shouldBe(enabled).click();
    }

    public void scrollToSearchArea() {
        ticketSearchArea.scrollTo();
    }
}
