package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static tests.TestData.userName;

public class ProfilePage {

    private final SelenideElement userNameValue = $("#userName-value");
    private final SelenideElement booksTable = $(".ReactTable");
    private final SelenideElement deleteBook = $("#delete-record-undefined");
    private final SelenideElement deleteBookConfirmation = $("#closeSmallModal-ok");

    @Step("Открытие страницы профиля пользователя")
    public void openProfilePage() {
        open("/profile");
    }

    @Step("Проверка отображения User Name в профиле")
    public void checkUserName() {
        userNameValue.shouldHave(text(userName));
    }

    @Step("Проверка наличия книги в профиле")
    public void checkBookInProfile() {
        booksTable.shouldHave(text("Git Pocket Guide"));
    }

    @Step("Удаление книги из профиля")
    public void deleteBookFromProfile() {
        deleteBook.click();
        deleteBookConfirmation.click();
    }

    @Step("Проверка отсутствия книги в профиле")
    public void checkBookWasRemovedFromProfile() {
        booksTable.shouldNotHave(text("Git Pocket Guide"));
    }

}
