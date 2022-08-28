package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.pages.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthDbInteractinDbUtilsTest {

    @BeforeAll
    public static void resetSUT() {
        DataHelper.clearSUTData();
        DataHelper.resetSUTData();
    }

    @BeforeEach
    void shouldStart() {
        open("http://localhost:9999/");
    }

    @AfterAll
    public static void cleanDatabase() {
        DataHelper.clearSUTData();
    }

    @Test
    void shouldValidAuthForVasyaAccountTest() {
        var loginPage = new LoginPage();
        var authInfoForVasya = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfoForVasya.getLogin(), authInfoForVasya.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfoForVasya);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldValidAuthForAddNewFakerAccountTest() {
        var loginPage = new LoginPage();
        var authIntoFaker = DataHelper.createUser();
        var verificationPage = loginPage.validLogin(authIntoFaker.getLogin(), authIntoFaker.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authIntoFaker);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldCheckThreeTimesInvalidPassInputForVasyaTest() {
        var authInfoForVasya = DataHelper.getAuthInfo();
        String invalidPass1 = DataHelper.getRandPass();
        String invalidPass2 = DataHelper.getRandPass();
        String invalidPass3 = DataHelper.getRandPass();

        var loginPage = new LoginPage();
        loginPage.enterLogin(authInfoForVasya.getLogin(), invalidPass1);
        loginPage.errorNotification();

        loginPage.cleanInputField();
        loginPage.enterLogin(authInfoForVasya.getLogin(), invalidPass2);
        loginPage.errorNotification();

        loginPage.cleanInputField();
        loginPage.enterLogin(authInfoForVasya.getLogin(), invalidPass3);
        loginPage.errorNotification();

        loginPage.cleanInputField();
        loginPage.enterLogin(authInfoForVasya.getLogin(), authInfoForVasya.getPassword());
        loginPage.errorNotification();
    }
}