package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObject.MainPage;
import steps.Steps;

import java.time.Duration;

import static driver.WebDriverCreator.createWebDriver;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CheckGoToSectionInConstructionTest extends BaseTest {
    private WebDriver webDriver;

    private static final String urlMainPage = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setup() {
        webDriver = createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }

    @Test
    @DisplayName("Конструктор блюд")
    @Description("Проверяем переход между разделами в конструкторе")
    public void checkGoToSectionConstruction() {

        MainPage mainPage = new MainPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlMainPage);
        steps.windowMaximize();
        mainPage.checkVisibilityOfElementInPage(mainPage.enterInAccountButton);

        mainPage.clickElement(mainPage.fillingLinkInConstructor);
        mainPage.checkVisibilityOfElementInPage(mainPage.filling);
        mainPage.clickElement(mainPage.saucesLinkInConstructor);
        mainPage.checkVisibilityOfElementInPage(mainPage.sauces);
        mainPage.clickElement(mainPage.bunLinkInConstructor);
        mainPage.checkVisibilityOfElementInPage(mainPage.bun);
    }

    @After
    public void teardown() {
        step("Закрываем браузер");
        {
            webDriver.quit();
        }
    }
}
