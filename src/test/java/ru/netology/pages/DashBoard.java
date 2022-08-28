package ru.netology.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashBoard {

    public DashBoard() {
        $(byText("Личный кабинет")).shouldBe(visible);
    }
}
