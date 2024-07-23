package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

public class RegistrationPage {

    private WebDriver webDriver;

    public RegistrationPage(WebDriver driver) {
        this.webDriver = driver;
    }

    public By buttonRegistration = By.xpath(".//button[text()='Зарегистрироваться']");

    public By titleFormRegistration = By.xpath(".//h2[text()='Регистрация']");

    public By inputNameElement = By.xpath(".//input[@name='name']");

    public By inputPasswordElement = By.xpath(".//input[@name='Пароль']");

    public By errorValidationPasswordText = By.xpath(".//p[text()='Некорректный пароль']");

    public By loginInAccountLink = By.xpath(".//a[text()='Войти']");

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

    public void inputTextInFindElements(By element, String text, Integer number) {
        step("Вводим текст " + text + "в инпут ввода");
        {
            webDriver.findElements(element).get(number).sendKeys(text);
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
