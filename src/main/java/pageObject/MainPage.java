package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.qameta.allure.Allure.step;

public class MainPage {

    private WebDriver webDriver;

    public MainPage(WebDriver driver) {
        this.webDriver = driver;
    }

    public By enterInAccountButton = By.xpath(".//button[text()='Войти в аккаунт']");

    public By buttonInHeaderPage = By.xpath(".//p[text()='Личный Кабинет']");

    public By bunLinkInConstructor = By.xpath(".//span[text()='Булки']");

    public By saucesLinkInConstructor = By.xpath(".//span[text()='Соусы']");

    public By fillingLinkInConstructor = By.xpath(".//span[text()='Начинки']");

    public By filling = By.xpath(".//p[text()='Биокотлета из марсианской Магнолии']");

    public By sauces = By.xpath(".//p[text()='Соус фирменный Space Sauce']");

    public By bun = By.xpath(".//p[text()='Флюоресцентная булка R2-D3']");


    public By titlePage = By.xpath(".//h1[text()='Соберите бургер']");

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

    public void checkVisibilityOfElementInPage(By element) {
        step("Проверяем отображение элемента на экране");
        {
            webDriver.findElement(element).isDisplayed();
        }
    }

}
