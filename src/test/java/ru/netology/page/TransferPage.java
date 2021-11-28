package ru.netology.page;

import com.sun.security.jgss.AuthorizationDataEntry;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.netology.domain.DataHelper;

import java.util.Collection;

import static io.restassured.RestAssured.given;

@Data
@AllArgsConstructor
public class TransferPage {
    private String token;

    public int getFirstCardBalance(TransferPage transferPage) {
        return getCardInfo(transferPage.token).path("[1].balance");

    }

    public int getSecondCardBalance(TransferPage transferPage) {
        return getCardInfo(transferPage.token).path("[0].balance");

    }

    public void transfer (TransferPage transferPage, DataHelper.TransferInfo info) {
        given()
                .baseUri("http://localhost")
                .port(9999)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+transferPage.token)
                .body(info)
            .when()
                .post("/api/transfer")
            .then()
                .statusCode(200);
    }

    private Response getCardInfo (String token) {
        Response response =
        given()
                .baseUri("http://localhost")
                .port(9999)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+token)
             .when()
                .get("/api/cards")
             .then()
                .statusCode(200)
             .extract()
                 .response();
    return response;
    }
}
