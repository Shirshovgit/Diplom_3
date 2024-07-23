package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

public class PersonalAccount {

    private WebDriver webDriver;

    public PersonalAccount(WebDriver driver) {
        this.webDriver = driver;
    }

    public By titlePage = By.xpath(".//h1[text()='Соберите бургер']");

    public void checkVisibilityOfElementInPage(By element) {
        step("Проверяем отображение элемента на экране");
        {
            webDriver.findElement(element).isDisplayed();
        }
    }

    public void clickElement(By element) {
        step("Кликаем по элементу");
        {
            webDriver.findElement(element).click();
        }
    }

    public void clickElement(By element, Integer index) {
        step("Кликаем по элементу");
        {
            webDriver.findElements(element).get(index).click();
        }
    }

}
