package ru.netology.page;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.netology.domain.DataHelper;

import static io.restassured.RestAssured.given;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        given()
                    .baseUri("http://localhost")
                    .port(9999)
                    .contentType(ContentType.JSON)
                    .body(info)
                .when()
                    .post("/api/auth")
                .then()
                    .statusCode(200);
        return new VerificationPage();
    }
}
