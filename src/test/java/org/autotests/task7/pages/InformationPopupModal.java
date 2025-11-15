package org.autotests.task7.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InformationPopupModal {

    /*
    В каждом файле с POM у каждого WebElement должна быть аннотация @FindBy и функция с методом initElements().
    За выполнение этого пункта – 1 балл. Если хотя бы в одном файле такого нет – 0 баллов.

    В каждом файле с POM названия переменных несут смысл их назначения, названия функций также несут смысловую нагрузку.
Если все это выполнено – 2 балла. Если пара переменных или названий функций не несут смысла их назначения, то оценка – 1 балл. Если больше двух – 0 баллов.

===========

Убедиться, что появилось всплывающее окно, которое содержит следующие заголовки: «Подготовка к полету», «Полезная информация», «О компании».
     */
    By preparingForFlightTitle = By.cssSelector("a[href='/information#flight']");
    By usefulInfoTitle = By.cssSelector("a[href='/information#useful']");
    By aboutCompanyTitle = By.cssSelector("a[href='/information#company']");

    WebDriver driver;

    public InformationPopupModal(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement getPreparingForFlightElement() {
        return driver.findElement(preparingForFlightTitle);
    }

    private WebElement getUsefulInfoElement() {
        return driver.findElement(usefulInfoTitle);
    }

    private WebElement getAboutCompanyElement() {
        return driver.findElement(aboutCompanyTitle);
    }

    public void stepCheckInformationPopupModalIsDisplayed(String preparingForFlightText, String usefulInfoText,
                                                          String aboutCompanyText) {
        Assertions.assertEquals(preparingForFlightText, getPreparingForFlightElement().getText());
        Assertions.assertEquals(usefulInfoText, getUsefulInfoElement().getText());
        Assertions.assertEquals(aboutCompanyText, getAboutCompanyElement().getText());
    }
}
