package tests.registration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class CheckRegistrationTests extends TestBase {

    private static final String USER_DATA = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
    private static final String DATA_WITHOUT_PASSWORD = "{ \"email\": \"eve.holt@reqres.in\"}";
    private static final String DATA_WITHOUT_EMAIL = "{ \"password\": \"pistol\" }";
    private static final String API_PATH = "/register";

    @Test
    @DisplayName("Проверяет успешную регистрацию пользователя")
    void checkSuccessfulRegistrationTest() {
        given()
                .body(USER_DATA)
                .contentType(JSON)
                .log().body()
                .when()
                .post(API_PATH)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }

    @Test
    @DisplayName("Проверяет ошибку регистрации при отсутствии ввода пароля")
    void checkRegistrationWithoutPasswordTest() {
        given()
                .body(DATA_WITHOUT_PASSWORD)
                .contentType(JSON)
                .log().body()
                .when()
                .post(API_PATH)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Проверяет ошибку регистрации при отсутствии ввода e-mail")
    void checkRegistrationWithoutEmailTest() {
        given()
                .body(DATA_WITHOUT_EMAIL)
                .contentType(JSON)
                .log().body()
                .when()
                .post(API_PATH)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}