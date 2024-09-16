package tests.users;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class CheckCreateUserTests extends TestBase {

    private static final String USER_DATA = "{ \"name\": \"Alexander\",\"job\": \"QA\" }";
    private static final String INVALID_PARAMETERS = "{ \"name\": \"Alexander\" \"job\": \"QA\" }";
    private static final String API_PATH = "/users";

    @Test
    @DisplayName("Проверяет успешное создание пользователя")
    void checkSuccessfulCreateUserTest() {
        given()
                .body(USER_DATA)
                .contentType(JSON)
                .log().body()
                .when()
                .post(API_PATH)
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Alexander"))
                .body("job", is("QA"));
    }

    @Test
    @DisplayName("Проверяет статус код ответа при создании пользователя с невалидными параметрами")
    void checkUnsuccessfulCreateUserTest() {
        given()
                .body(INVALID_PARAMETERS)
                .contentType(JSON)
                .log().body()
                .when()
                .post(API_PATH)
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }
}