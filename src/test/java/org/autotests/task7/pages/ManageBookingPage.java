package org.autotests.task7.pages;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class ManageBookingPage {
    /*
    В каждом файле с POM у каждого WebElement должна быть аннотация @FindBy и функция с методом initElements().
    За выполнение этого пункта – 1 балл. Если хотя бы в одном файле такого нет – 0 баллов.

============

6. Убедиться, что в новой вкладке на экране отображается текст ошибки «Заказ с указанными параметрами не найден».

     */

    By orderOrTicketNumberFieldSelector = By.cssSelector("input[placeholder='Номер бронирования или билета']");
    By clientLastnameFieldSelector = By.cssSelector("input[placeholder='Фамилия клиента']");
    By searchButtonSelector = By.xpath("//button[.//span[contains(text(), 'Поиск')]]");

    WebDriver driver;

    public ManageBookingPage(WebDriver driver) {
        this.driver = driver;
    }

    public void stepCheckThePageIsDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderOrTicketNumberFieldSelector));
        Assertions.assertTrue(driver.findElement(orderOrTicketNumberFieldSelector).isDisplayed());
        Assertions.assertTrue(driver.findElement(clientLastnameFieldSelector).isDisplayed());
        Assertions.assertTrue(driver.findElement(searchButtonSelector).isDisplayed());
    }

    private void enterOrderOrTicketNumber(String orderOrTicketNumber) {
        driver.findElement(orderOrTicketNumberFieldSelector).click();
        driver.findElement(orderOrTicketNumberFieldSelector).sendKeys(orderOrTicketNumber);
    }

    private void enterLastname(String lastname) {
        driver.findElement(clientLastnameFieldSelector).click();
        driver.findElement(clientLastnameFieldSelector).sendKeys(lastname);
    }

    private void clickSearchButton() {
        driver.findElement(searchButtonSelector).click();
    }

    @SneakyThrows
    public void stepSearchForTicketOrder(String orderOrTicketNumber, String lastname) {
        enterLastname(lastname);
        enterOrderOrTicketNumber(orderOrTicketNumber);


        //String originalWindow = driver.getWindowHandle();
        //Set<String> oldTabs = driver.getWindowHandles();

        clickSearchButton();
        Thread.sleep(30000);

        //new WebDriverWait(driver, Duration.ofSeconds(100))
        //        .until(driver1 -> driver1.getWindowHandles().size() > oldTabs.size());

        //for (String handle : driver.getWindowHandles()) {
        //    if (oldTabs.contains(handle)) {
        //        driver.switchTo().window(handle);
        //        break;
        //    }
        //}

        //new WebDriverWait(driver, Duration.ofSeconds(10))
        //        .until(web -> ((JavascriptExecutor) web)
        //                .executeScript("return document.readyState").toString().equals("complete"));

    }
}
