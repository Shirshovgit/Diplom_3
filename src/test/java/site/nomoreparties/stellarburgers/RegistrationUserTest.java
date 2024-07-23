package site.nomoreparties.stellarburgers;

import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.LoginPage;
import pageObject.PersonalAccount;
import pageObject.RegistrationPage;
import steps.Steps;

import java.time.Duration;


import static driver.WebDriverCreator.createWebDriver;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.SECONDS;

public class RegistrationUserTest extends BaseTest {

    public String userFieldsJson(String email, String password, String userName) {
        String json = "{\"email\": \"" + email + "\", \"password\":\"" + password + "\", \"name\": \"" + userName + "\"}";
        return json;
    }

    private WebDriver webDriver;

    private static final String urlUserRegister = "https://stellarburgers.nomoreparties.site/register";

    private String email = userFieldsCreate("Email");
    private String password = userFieldsCreate("Password");

    private String nameUser = userFieldsCreate("Name");

    Integer i = 0;

    @Before
    public void setup() {
        webDriver = createWebDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));

    }

    @Test
    @Description("Проверяем флоу успешной регистрации пользователя")
    public void shouldSuccessRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlUserRegister);
        steps.windowMaximize();
        registrationPage.checkVisibilityOfElementInPage(registrationPage.buttonRegistration);

        registrationPage.inputTextInFindElements(registrationPage.inputNameElement, nameUser, 0);
        registrationPage.inputTextInFindElements(registrationPage.inputNameElement, email, 1);
        registrationPage.inputTextInFindElement(registrationPage.inputPasswordElement, password);

        registrationPage.clickElement(registrationPage.buttonRegistration);

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.checkVisibilityOfElementInPage(loginPage.titleLoginForm);
        loginPage.inputTextInFindElement(loginPage.inputNameElement, email);
        loginPage.inputTextInFindElement(loginPage.inputPasswordElement, password);
        loginPage.clickElement(loginPage.loginButton);

        PersonalAccount personalAccount = new PersonalAccount(webDriver);
        personalAccount.checkVisibilityOfElementInPage(personalAccount.titlePage);
    }

    @Test
    @Description("Проверяем минимальную длину для поля - Пароль")
    public void checkValidationInputPassword() {
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        Steps steps = new Steps(webDriver);

        steps.goToUrl(urlUserRegister);
        steps.windowMaximize();
        registrationPage.checkVisibilityOfElementInPage(registrationPage.buttonRegistration);

        while (i < 5) {
            i++;
            registrationPage.inputTextInFindElement(registrationPage.inputPasswordElement, "1");
            registrationPage.clickElement(registrationPage.inputNameElement, 1);
            registrationPage.checkVisibilityOfElementInPage(registrationPage.errorValidationPasswordText);
            registrationPage.clickElement(registrationPage.buttonRegistration);
            registrationPage.checkVisibilityOfElementInPage(registrationPage.titleFormRegistration);
        }
        registrationPage.inputTextInFindElement(registrationPage.inputPasswordElement, "1");
        registrationPage.inputTextInFindElements(registrationPage.inputNameElement, nameUser, 0);
        registrationPage.inputTextInFindElements(registrationPage.inputNameElement, email, 1);
        registrationPage.clickElement(registrationPage.buttonRegistration);

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.checkVisibilityOfElementInPage(loginPage.titleLoginForm);

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
