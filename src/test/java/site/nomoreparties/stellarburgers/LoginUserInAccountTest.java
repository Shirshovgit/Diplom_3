package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.*;
import steps.Steps;

import java.time.Duration;

import static driver.WebDriverCreator.createWebDriver;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.SECONDS;

public class LoginUserInAccountTest extends BaseTest {

    private String userFieldsJson(String email, String password, String userName) {
        String json = "{\"email\": \"" + email + "\", \"password\":\"" + password + "\", \"name\": \"" + userName + "\"}";
        return json;
    }

    private String email = userFieldsCreate("Email");
    private String password = userFieldsCreate("Password");
    ;
    private String nameUser = userFieldsCreate("Name");

    private WebDriver webDriver;

    private static final String urlMainPage = "https://stellarburgers.nomoreparties.site";

    private static final String urlUserRegister = "https://stellarburgers.nomoreparties.site/register";

    private static final String urlForgotPassword = "https://stellarburgers.nomoreparties.site/forgot-password";


    @Before
    public void setup() {
        webDriver = createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }

    @Test
    @DisplayName("Вход в аккаунт")
    @Description("Проверяем вход в аккаунт через кнопку Войти в акаунт на главной странице")
    public void shouldSuccessLoginInButtonMainPage() {
        createApiUser();

        MainPage mainPage = new MainPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlMainPage);
        steps.windowMaximize();
        mainPage.checkVisibilityOfElementInPage(mainPage.enterInAccountButton);

        mainPage.clickElement(mainPage.enterInAccountButton);

        loginInAccountAndCheckPersonalAccountOpen();

    }

    @Test
    @DisplayName("Вход в аккаунт")
    @Description("Проверяем вход в аккаунт через кнопку Войти в акаунт на главной странице")
    public void shouldSuccessLoginPersonalAccountButtonInMainPage() {

        createApiUser();

        MainPage mainPage = new MainPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlMainPage);
        steps.windowMaximize();
        mainPage.checkVisibilityOfElementInPage(mainPage.enterInAccountButton);

        mainPage.clickElement(mainPage.buttonInHeaderPage);

        loginInAccountAndCheckPersonalAccountOpen();

    }

    @Test
    @DisplayName("Вход в аккаунт")
    @Description("Проверяем вход в аккаунт через кнопку Войти в акаунт на странице регистрации")
    public void shouldSuccessLoginButtonInRegistrationPage() {

        createApiUser();

        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlUserRegister);
        steps.windowMaximize();
        registrationPage.checkVisibilityOfElementInPage(registrationPage.buttonRegistration);
        registrationPage.clickElement(registrationPage.loginInAccountLink);


        loginInAccountAndCheckPersonalAccountOpen();

    }

    @Test
    @DisplayName("Вход в аккаунт")
    @Description("Проверяем вход в аккаунт через кнопку Войти в акаунт на странице восстановления пароля")
    public void shouldSuccessLoginButtonInForgotPasswordPage() {

        createApiUser();

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlForgotPassword);
        steps.windowMaximize();
        forgotPasswordPage.checkVisibilityOfElementInPage(forgotPasswordPage.forgotPasswordFormTitle);
        forgotPasswordPage.clickElement(forgotPasswordPage.loginInAccountLink);


        loginInAccountAndCheckPersonalAccountOpen();

    }

    private void loginInAccountAndCheckPersonalAccountOpen() {
        step("Входим в аккаунт и проверяем, что личный кабинет загрузился");
        {
            LoginPage loginPage = new LoginPage(webDriver);
            loginPage.checkVisibilityOfElementInPage(loginPage.titleLoginForm);
            loginPage.inputTextInFindElement(loginPage.inputNameElement, email);
            loginPage.inputTextInFindElement(loginPage.inputPasswordElement, password);
            loginPage.clickElement(loginPage.loginButton);

            MainPage mainPage = new MainPage(webDriver);
            mainPage.checkVisibilityOfElementInPage(mainPage.titlePage);
            mainPage.clickElement(mainPage.buttonInHeaderPage);

            ProfilePage profilePage = new ProfilePage(webDriver);
            profilePage.checkVisibilityOfElementInPage(profilePage.profileLink);
            profilePage.checkVisibilityOfElementInPage(profilePage.historyOrder);
            profilePage.checkVisibilityOfElementInPage(profilePage.checkOutLink);
        }
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
