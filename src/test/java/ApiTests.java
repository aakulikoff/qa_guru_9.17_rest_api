import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static Utils.FileUtils.readStringFromFile;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests {

    @BeforeAll
    static void setUrl() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    public  void createUser () {
        String requestData = readStringFromFile("src/test/resources/data/createUser/requestData.json");
        String actualResponse =
                given()
                        .contentType(JSON)
                        .body(requestData)
                        .when()
                        .post("/api/users")
                        .then()
                        .statusCode(201).extract().asString();

        String expectedResponse = readStringFromFile("src/test/resources/data/createUser/expectedResponse.json");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void successfulLogin() {
        String requestData = readStringFromFile("src/test/resources/data/loginUser/requestData.json");
        String actualResponse =
        given()
                .contentType(JSON)
                .body(requestData)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200).extract().asString();

        String expectedResponse = readStringFromFile("src/test/resources/data/loginUser/expectedResponse.json");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void successfulLogin2() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerUnsucessful() {
        String requestData = readStringFromFile("src/test/resources/data/registerUser/requestData.json");
        String actualResponse =
                given()
                        .contentType(JSON)
                        .body(requestData)
                        .when()
                        .post("/api/login")
                        .then()
                        .statusCode(400).extract().asString();

        String expectedResponse = readStringFromFile("src/test/resources/data/registerUser/expectedResponse.json");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void listUser() {
        String actualResponse =
                given()
                        .contentType(JSON)
                        .when()
                        .get("/api/users?page=2")
                        .then()
                        .statusCode(200).extract().asString();

        String expectedResponse = readStringFromFile("src/test/resources/data/listUsers/expectedResponse.json");
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteUser() {
        given()
                .contentType(JSON)
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }
}
