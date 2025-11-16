package org.autotests.task8.pom;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$;

public class InformationPopupSelenide {

    private final SelenideElement preparingForFlightElement = $("a[href='/information#flight']");
    private final SelenideElement usefulInfoElement = $("a[href='/information#useful']");
    private final SelenideElement aboutCompanyElement = $("a[href='/information#company']");

    @Step("Убедиться, что появилось всплывающее окно, которое содержит заголовки {preparingForFlightText}, " +
            "{usefulInfoText}, {aboutCompanyText}")
    public void stepCheckInformationPopupModalIsDisplayed(String preparingForFlightText, String usefulInfoText,
                                                          String aboutCompanyText) {
        Assertions.assertEquals(preparingForFlightText, preparingForFlightElement.getText());
        Assertions.assertEquals(usefulInfoText, usefulInfoElement.getText());
        Assertions.assertEquals(aboutCompanyText, aboutCompanyElement.getText());
    }
}
