package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.util.Locale;

public class DataHelper {
    public DataHelper() {
    }

    @Value
    public static class User {
        private String login;
        private String password;

    }

    public static User getUser() {
        return new User("vasya", "qwerty123");
    }

    public static User getUserWithInvalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        return new User("vasya", faker.internet().password());
    }

    public static User getFakerUser() {
        Faker faker = new Faker(new Locale("en"));
        return new User(faker.name().username(), faker.internet().password());
    }

    @SneakyThrows
    public static String getCode() {
        var runner = new QueryRunner();
        var getId = "SELECT code FROM auth_codes WHERE user_id= (select id from users where login=?) order by created DESC;";

        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/app", "app", "pass");) {
            return runner.query(conn, getId, getUser().getLogin(), new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static String getStatusUser() {
        var runner = new QueryRunner();
        var getId = "SELECT status FROM users WHERE login=?";

        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/app", "app", "pass");) {
            return runner.query(conn, getId, getUser().getLogin(), new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void cleanData() {
        var runner = new QueryRunner();
        var cleanCodes = "delete from auth_codes";
        var cleanCards = "delete from cards";
        var cleanUsers = "delete from users;";

        try (var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/app", "app", "pass");) {
            runner.update(conn, cleanCodes);
            runner.update(conn, cleanCards);
            runner.update(conn, cleanUsers);
        }
    }
}
