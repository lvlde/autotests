package org.autotests.task7.pages;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InformationPopup {

    @Getter
    @FindBy(css = "a[href='/information#flight']")
    WebElement preparingForFlightElement;

    @Getter
    @FindBy(css = "a[href='/information#useful']")
    WebElement usefulInfoElement;

    @Getter
    @FindBy(css = "a[href='/information#company']")
    WebElement aboutCompanyElement;

    WebDriver driver;

    public InformationPopup(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void stepCheckInformationPopupModalIsDisplayed(String preparingForFlightText, String usefulInfoText,
                                                          String aboutCompanyText) {
        Assertions.assertEquals(preparingForFlightText, getPreparingForFlightElement().getText());
        Assertions.assertEquals(usefulInfoText, getUsefulInfoElement().getText());
        Assertions.assertEquals(aboutCompanyText, getAboutCompanyElement().getText());
    }
}
