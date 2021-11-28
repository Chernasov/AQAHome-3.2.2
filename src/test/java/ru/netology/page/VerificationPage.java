package ru.netology.page;

import io.restassured.http.ContentType;
import ru.netology.domain.DataHelper;

import static io.restassured.RestAssured.given;

public class VerificationPage {
    public TransferPage validVerify(DataHelper.VerificationCode verificationCode) {
        String token =
        given()
                .baseUri("http://localhost")
                .port(9999)
                .contentType(ContentType.JSON)
                .body(verificationCode)
             .when()
                .post("/api/auth/verification")
             .then()
                .statusCode(200)
             .extract()
                .path("token");
        return new TransferPage(token);
    }
}
