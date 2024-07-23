package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

public class ProfilePage {

    public By profileLink = By.xpath(".//a[text()='Профиль']");

    public By historyOrder = By.xpath(".//a[text()='История заказов']");

    public By checkOutLink = By.xpath(".//button[text()='Выход']");

    private WebDriver webDriver;

    public ProfilePage(WebDriver driver) {
        this.webDriver = driver;
    }

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
}
