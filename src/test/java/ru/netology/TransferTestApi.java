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
        sendApi.sendPost(validUser, DataHelper.getPathAuth().getPath(), "");
        var verificationCode = DataHelper.getVerificationCodeFor();
        String token = sendApi.getToken(verificationCode, DataHelper.getPathVerification().getPath());
        var balanceCardFirstBefore = sendApi.getFirstCardBalance(token);
        var balanceCardSecondBefore = sendApi.getSecondCardBalance(token);
        var amount = 5000;
        var transfer = new DataHelper.TransferInfo("5559 0000 0000 0002", "5559 0000 0000 0001", amount);
        sendApi.transfer(token, transfer, DataHelper.getPathTransfer().getPath());
        var balanceCardFirstAfter = sendApi.getFirstCardBalance(token);
        var balanceCardSecondAfter = sendApi.getSecondCardBalance(token);
        assertEquals(balanceCardFirstBefore + amount, balanceCardFirstAfter);
        assertEquals(balanceCardSecondBefore - amount, balanceCardSecondAfter);
    }
}
