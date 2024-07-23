package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

public class LoginPage {
    private WebDriver webDriver;

    public LoginPage(WebDriver driver) {
        this.webDriver = driver;
    }

    public By inputNameElement = By.xpath(".//input[@name='name']");

    public By inputPasswordElement = By.xpath(".//input[@name='Пароль']");

    public By titleLoginForm = By.xpath(".//h2[text()='Вход']");

    public By loginButton = By.xpath(".//button[text()='Войти']");

    public void checkVisibilityOfElementInPage(By element) {
        step("Проверяем отображение элемента на экране");
        {
            webDriver.findElement(element).isDisplayed();
        }
    }

    public void inputTextInFindElement(By element, String text) {
        step("Вводим текст " + text + "в инпут ввода");
        {
            webDriver.findElement(element).sendKeys(text);
        }
    }

    public void clickElement(By element) {
        step("Кликаем по элементу");
        {
            webDriver.findElement(element).click();
        }
    }
}
