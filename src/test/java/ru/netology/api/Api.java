package ru.netology.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.netology.domain.DataHelper;

import static io.restassured.RestAssured.given;

public class Api {
    public Response sendPost(Object info, String path) {
        Response response =
                given()
                        .baseUri("http://localhost")
                        .port(9999)
                        .contentType(ContentType.JSON)
                        .body(info)
                        .when()
                        .post(path)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return response;
    }

    public String getToken(DataHelper.VerificationCode verificationCode, String path) {
        return sendPost(verificationCode, path).path("token");
    }

    private Response getCardInfo(String token) {
        Response response =
                given()
                        .baseUri("http://localhost")
                        .port(9999)
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/api/cards")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
        return response;
    }

    public int getFirstCardBalance(String token) {
        return getCardInfo(token).path("[1].balance");
    }

    public int getSecondCardBalance(String token) {
        return getCardInfo(token).path("[0].balance");
    }

    public void transfer(String token, DataHelper.TransferInfo info) {

        given()
                .baseUri("http://localhost")
                .port(9999)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(info)
                .when()
                .post("/api/transfer")
                .then()
                .statusCode(200);
    }
}
