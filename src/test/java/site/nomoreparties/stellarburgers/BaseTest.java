package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Random;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class BaseTest {

    enum statusCode {
        SUCCESS_200(200),
        SUCCESS_201(201),
        SUCCESS_202(202),
        FAILED_404(404),
        FAILED_401(401),

        FAILED_400(400),
        FAILED_403(403),
        FAILED_500(500),
        FAILED_409(409);


        final int code;

        statusCode(int code) {
            this.code = code;
        }

    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    public String pathCreateUser = "/api/auth/register";
    public String pathLoginUser = "/api/auth/login";

    public String pathAuthUser = "/api/auth/user";

    public String pathOrder = "/api/orders";
    Random random = new Random();

    public String userFieldsCreate(String Case) {
        switch (Case) {
            case "Name":
                return "name" + random.nextInt(10000000);
            case "Email":
                return "something" + random.nextInt(10000000) + "@yandex.ru";
            case "Password":
                return "passsword" + random.nextInt(10000000);
        }
        return null;
    }

    public Response sendPostRequest(String pathRequest, String pathBody) {
        step("Отправляем Post в ручку " + pathRequest);
        {
            Response response = given().auth().none()
                    .header("Content-type", "application/json")
                    .body(pathBody)
                    .post(pathRequest);
            return response;
        }
    }

    @Step("Сравниваем тело ответа")
    public void compareBodyResponse(Response response, String parameterName, String messageText) {
        response.then().assertThat().body(parameterName, equalTo(messageText));
    }

    @Step("Сравниваем тело ответа")
    public void compareBodyResponse(Response response, String parameterName, boolean value) {
        response.then().assertThat().body(parameterName, equalTo(value));
    }

    @Step("Получаем accessToken юзера")
    public String getAccessToken(Response response) {
        if (response.jsonPath().get("success").toString() == "true") {
            return response.jsonPath().get("accessToken").toString();
        }
        return null;
    }

    @Step("Сравниваем статус кода ответа")
    public void compareStatusCodeResponse(Response response, Integer code) {
        Assert.assertTrue("Проверка завершилось ошибкой " + response.jsonPath().get().toString() + "Code: " + response.thenReturn().statusCode(),
                response.thenReturn().statusCode() == code);
    }

    public Response sendDeleteRequest(String pathRequest, String bearerToken) {
        step("Отправляем Delete в ручку" + pathRequest);
        {
            Response response = given().auth().none()
                    .header("Content-type", "application/json")
                    .header("authorization", bearerToken)
                    .delete(pathRequest);
            return response;
        }
    }
}
