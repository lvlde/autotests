package org.autotests.task7.pages;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InformationPopup {

    //By preparingForFlightTitle = By.cssSelector("a[href='/information#flight']");
    //By usefulInfoTitle = By.cssSelector("a[href='/information#useful']");
    //By aboutCompanyTitle = By.cssSelector("a[href='/information#company']");

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

//    private WebElement getPreparingForFlightElement() {
//        return driver.findElement(preparingForFlightTitle);
//    }

//    private WebElement getUsefulInfoElement() {
//        return driver.findElement(usefulInfoTitle);
//    }

//    private WebElement getAboutCompanyElement() {
//        return driver.findElement(aboutCompanyTitle);
//    }

    public void stepCheckInformationPopupModalIsDisplayed(String preparingForFlightText, String usefulInfoText,
                                                          String aboutCompanyText) {
        Assertions.assertEquals(preparingForFlightText, getPreparingForFlightElement().getText());
        Assertions.assertEquals(usefulInfoText, getUsefulInfoElement().getText());
        Assertions.assertEquals(aboutCompanyText, getAboutCompanyElement().getText());
    }
}
