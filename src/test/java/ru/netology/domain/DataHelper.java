package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String login;
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeFor() {
        var codesSQL = "SELECT code FROM auth_codes ORDER BY created DESC;";
        var runner = new QueryRunner();
        String code;
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app_db", "user", "password"
                )
        ) {
            code = runner.query(conn, codesSQL, new ScalarHandler<>());
        }
        return new VerificationCode("vasya", code);
    }

    @Value
    public static class CardInfo {
        private String id;
        private String number;
        private int balance;
    }

    @Value
    @AllArgsConstructor
    public static class TransferInfo {
        private String from;
        private String to;
        private int amount;
    }

    @Value
    public static class Pathes {
        private String path;
    }

    public static Pathes getPathAuth() {
        return new Pathes("/api/auth");
    }

    public static Pathes getPathVerification() {
        return new Pathes("/api/auth/verification");
    }

    public static Pathes getPathTransfer() {
        return new Pathes("/api/transfer");
    }
}
