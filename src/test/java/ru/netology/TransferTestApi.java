package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.domain.DataHelper;
import ru.netology.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTestApi {

    @Test
    void shouldTransferFromSecondToFirst() {
        var loginPage = new LoginPage();
        var validUser = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(validUser);
        var verificationCode = DataHelper.getVerificationCodeFor();
        var transferPage = verificationPage.validVerify(verificationCode);
        var balanceCardFirstBefore = transferPage.getFirstCardBalance(transferPage);
        var balanceCardSecondBefore = transferPage.getSecondCardBalance(transferPage);
        var amount = 5000;
        var transfer = new DataHelper.TransferInfo("5559 0000 0000 0002", "5559 0000 0000 0001", amount);
        transferPage.transfer(transferPage, transfer);
        var balanceCardFirstAfter = transferPage.getFirstCardBalance(transferPage);
        var balanceCardSecondAfter = transferPage.getSecondCardBalance(transferPage);
        assertEquals(balanceCardFirstBefore + amount, balanceCardFirstAfter);
        assertEquals(balanceCardSecondBefore - amount, balanceCardSecondAfter);
    }
}
