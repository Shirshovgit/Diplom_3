package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

public class ForgotPasswordPage {

    private WebDriver webDriver;

    public ForgotPasswordPage(WebDriver driver) {
        this.webDriver = driver;
    }

    public By forgotPasswordFormTitle = By.xpath(".//h2[text()='Восстановление пароля']");

    public By loginInAccountLink = By.xpath(".//a[text()='Войти']");

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
