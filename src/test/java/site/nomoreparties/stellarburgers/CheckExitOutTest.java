package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageObject.LoginPage;
import pageObject.MainPage;
import pageObject.ProfilePage;
import steps.Steps;

import java.time.Duration;

import static driver.WebDriverCreator.createWebDriver;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CheckExitOutTest extends BaseTest {
    private String userFieldsJson(String email, String password, String userName) {
        String json = "{\"email\": \"" + email + "\", \"password\":\"" + password + "\", \"name\": \"" + userName + "\"}";
        return json;
    }

    private String email = userFieldsCreate("Email");
    private String password = userFieldsCreate("Password");
    private String nameUser = userFieldsCreate("Name");

    private WebDriver webDriver;
    private static final String urlMainPage = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setup() {
        webDriver = createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }

    @Test
    @DisplayName("Выход из аккаунт")
    @Description("Проверяем выход из личного кабинета")
    public void checkExitInAccount() {
        createApiUser();

        MainPage mainPage = new MainPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlMainPage);
        steps.windowMaximize();
        mainPage.checkVisibilityOfElementInPage(mainPage.enterInAccountButton);

        mainPage.clickElement(mainPage.enterInAccountButton);

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.checkVisibilityOfElementInPage(loginPage.titleLoginForm);
        loginPage.inputTextInFindElement(loginPage.inputNameElement, email);
        loginPage.inputTextInFindElement(loginPage.inputPasswordElement, password);
        loginPage.clickElement(loginPage.loginButton);

        mainPage.checkVisibilityOfElementInPage(mainPage.titlePage);
        mainPage.clickElement(mainPage.buttonInHeaderPage);

        ProfilePage profilePage = new ProfilePage(webDriver);
        profilePage.checkVisibilityOfElementInPage(profilePage.profileLink);
        profilePage.checkVisibilityOfElementInPage(profilePage.historyOrder);
        profilePage.checkVisibilityOfElementInPage(profilePage.checkOutLink);

        profilePage.clickElement(profilePage.checkOutLink);
        loginPage.checkVisibilityOfElementInPage(loginPage.titleLoginForm);

    }

    private void createApiUser() {
        step("Создаем юзера через API");
        {
            Response createUser = sendPostRequest(pathCreateUser, userFieldsJson(email, password, nameUser));
            compareStatusCodeResponse(createUser, BaseTest.statusCode.SUCCESS_200.code);
            compareBodyResponse(createUser, "success", true);
        }
    }

    @After
    public void teardown() {
        step("Закрываем браузер по завершению теста");
        {
            webDriver.quit();
        }
        Response checkLogin = sendPostRequest(pathLoginUser, userFieldsJson(email, password, nameUser));
        if (getAccessToken(checkLogin) != null) {
            step("Удаляем юзера");
            {
                Response deleteUser = sendDeleteRequest(pathAuthUser, getAccessToken(checkLogin));
                compareBodyResponse(deleteUser, "success", true);
                compareBodyResponse(deleteUser, "message", "User successfully removed");
                compareStatusCodeResponse(deleteUser, BaseTest.statusCode.SUCCESS_202.code);
            }
        }
    }
}
