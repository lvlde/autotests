package org.autotests.task7.pages;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchArea {
    /*
    В каждом файле с POM у каждого WebElement должна быть аннотация @FindBy и функция с методом initElements().
    За выполнение этого пункта – 1 балл. Если хотя бы в одном файле такого нет – 0 баллов.

    В каждом файле с POM названия переменных несут смысл их назначения, названия функций также несут смысловую нагрузку.
Если все это выполнено – 2 балла. Если пара переменных или названий функций не несут смысла их назначения, то оценка – 1 балл. Если больше двух – 0 баллов.

================

3. Проскроллить страницу к блоку поиска билета и убедиться, что блок с поиском билета действительно отображается (есть поле Откуда, Куда, Дата вылета Туда, Дата вылета Обратно)
4. Выбрать (или ввести) следующие критерии поиска:
откуда – Москва (без выбора аэропорта) + нажать Enter
куда – Санкт-Петербург + нажать Enter.
5. Нажать кнопку «Поиск».
6. Убедиться, что около поля «Туда» появилась красная обводка.

поле Откуда, Куда, Дата вылета Туда, Дата вылета Обратно)
     */
    By fromInputSelector = By.cssSelector("input[placeholder='Откуда']");
    By toInputSelector = By.cssSelector("input[placeholder='Куда']");
    By dateOfDepartureThereInputSelector = By.cssSelector("input[placeholder='Туда']");
    By dateOfDepartureBackInputSelector = By.cssSelector("input[placeholder='Обратно']");
    By ticketSearchButtonSelector = By.xpath("//button[@type='submit']//span[text()='Поиск']");
    //By parentOfDateOfDepartureThereInputSelector = By.xpath("//div[.//input[@placeholder='Туда']]");
    By parentOfDateOfDepartureThereInputSelector = By.cssSelector(".dp-1dr6zbu-root");

    WebDriver driver;

    public SearchArea(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement getDepartureField() {
        return driver.findElement(fromInputSelector);
    }

    private WebElement getDestinationField() {
        return driver.findElement(toInputSelector);
    }

    private WebElement getDateOfDepartureField() {
        return driver.findElement(dateOfDepartureThereInputSelector);
    }

    private WebElement getDateOfArrivalField() {
        return driver.findElement(dateOfDepartureBackInputSelector);
    }

    public void stepCheckTicketSearchAreaIsDisplayed() {
        Assertions.assertTrue(getDepartureField().isDisplayed());
        Assertions.assertTrue(getDestinationField().isDisplayed());
        Assertions.assertTrue(getDateOfDepartureField().isDisplayed());
        Assertions.assertTrue(getDateOfArrivalField().isDisplayed());
    }

    private void pickDeparturePoint(String departurePoint) {
        String inputValue = getDepartureField().getAttribute("value");
        if (inputValue == null) {
            getDepartureField().sendKeys(departurePoint);
        } else if (!inputValue.equals(departurePoint)) {
            getDepartureField().clear();
            getDepartureField().sendKeys(departurePoint);
        }
    }

    private void pickDestinationPoint(String destinationPoint) {
        getDestinationField().click();
        getDestinationField().sendKeys(destinationPoint);
    }

    private void clickSearchButton() {
        driver.findElement(ticketSearchButtonSelector).click();
    }

    public void stepSearchForFlight(String departurePoint, String destinationPoint) {
        pickDeparturePoint(departurePoint);
        pickDestinationPoint(destinationPoint);
        clickSearchButton();
    }

    @SneakyThrows
    public void stepCheckRedBorderAroundDateOfDepartureThereInputField() {
        Thread.sleep(3000);
        WebElement borderLine = driver.findElement(By.cssSelector(".dp-1dr6zbu-root[data-failed]"));
        //<div class="dp-1dr6zbu-root" data-failed="true"><input class="dp-zu3w2f-root-control" placeholder="Туда" readonly="" data-empty="true" value=""><div class="dp-1m6yfmj-root" inert="" data-empty="true" data-failed="true"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="none" viewBox="0 0 24 24" class="dp-1w5jbk6-root"><path fill="currentColor" fill-rule="evenodd" d="M6 2h2v2h8V2h2v2h1a3 3 0 0 1 3 3v2a1 1 0 1 1-2 0V7H5a1 1 0 0 0-1 1v9a2 2 0 0 0 2 2h3a1 1 0 1 1 0 2H6a4 4 0 0 1-4-4V8a4 4 0 0 1 4-4zm11 11a1 1 0 0 0-.707 1.707l.793.793-2.793 2.793a1 1 0 0 0 1.414 1.414l2.793-2.793.793.793A1 1 0 0 0 21 17v-3a1 1 0 0 0-1-1z" clip-rule="evenodd"></path></svg></div></div>
        String borderColor = borderLine.getCssValue("border-color");
        System.out.println(borderColor);


        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.elementSelectionStateToBe()
        //        .attributeToBe(driver.findElement(parentOfDateOfDepartureThereInputSelector),
        //                "data-failed", "true"));


        //String dataFailedValue = driver.findElement(parentOfDateOfDepartureThereInputSelector)
        //        .getAttribute("data-failed");
        Assertions.assertEquals("rgb(213, 0, 98)", borderColor);
    }
}
