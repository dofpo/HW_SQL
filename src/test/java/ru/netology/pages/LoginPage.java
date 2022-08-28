package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public LoginPage() {
    }

    public VerificationPage validLogin(DataHelper.User info) {
        authentification(info);
        return new VerificationPage();
    }

    public void authentification(DataHelper.User info) {

        SelenideElement loginField = $("[data-test-id='login'] input");
        SelenideElement passwordField = $("[data-test-id='password'] input");
        SelenideElement loginButton = $("[data-test-id='action-login']");

        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void errorMessage() {
        SelenideElement error = $(byText("Ошибка")).shouldBe(visible);
        SelenideElement errorSecond = $(byText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    public void errorMessageClose() {
        SelenideElement buttonClose = $("[data-test-id='error-notification'] button");
        buttonClose.click();
    }

    public void cleanField() {
        SelenideElement amountField = $("[data-test-id='login'] input");
        SelenideElement numberCardField = $("[data-test-id='password'] input");

        amountField.sendKeys(Keys.CONTROL + "A");
        amountField.sendKeys(Keys.DELETE);
        numberCardField.sendKeys(Keys.CONTROL + "A");
        numberCardField.sendKeys(Keys.DELETE);
    }
}