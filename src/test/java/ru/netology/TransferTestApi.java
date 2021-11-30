package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.api.Api;
import ru.netology.domain.DataHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTestApi {

    @Test
    void shouldTransferFromSecondToFirst() {
        var sendApi = new Api();
        var validUser = DataHelper.getAuthInfo();
        sendApi.sendPost(validUser, "/api/auth");
        var verificationCode = DataHelper.getVerificationCodeFor();
        String token = sendApi.getToken(verificationCode, "/api/auth/verification");
        var balanceCardFirstBefore = sendApi.getFirstCardBalance(token);
        var balanceCardSecondBefore = sendApi.getSecondCardBalance(token);
        var amount = 5000;
        var transfer = new DataHelper.TransferInfo("5559 0000 0000 0002", "5559 0000 0000 0001", amount);
        sendApi.transfer(token, transfer);
        var balanceCardFirstAfter = sendApi.getFirstCardBalance(token);
        var balanceCardSecondAfter = sendApi.getSecondCardBalance(token);
        assertEquals(balanceCardFirstBefore + amount, balanceCardFirstAfter);
        assertEquals(balanceCardSecondBefore - amount, balanceCardSecondAfter);
    }
}
