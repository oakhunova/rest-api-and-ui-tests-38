package api;

import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import models.BookDataModel;
import models.CredentialsModel;
import models.LoginResponseModel;

import java.util.Collections;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.ResponseSpec.responseSpec;
import static tests.TestData.password;
import static tests.TestData.userName;

public class RestApi {

    private LoginResponseModel loginResponseModel;
    String token;
    String userId;
    String expires;

    @Step("Авторизация")
    public void authApiPostRequest() {
        CredentialsModel credentialsData = new CredentialsModel();
        credentialsData.setUserName(userName);
        credentialsData.setPassword(password);

        loginResponseModel = given()
                    .body(credentialsData)
                    .contentType(JSON)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .spec(responseSpec(200))
                    .extract().as(LoginResponseModel.class);
            this.token = loginResponseModel.getToken();
            this.userId = loginResponseModel.getUserId();
            this.expires = loginResponseModel.getExpires();

    }

    @Step("Очистка корзины")
    public void deleteAllBooks() {
        given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponseModel.getToken())
                .queryParam("UserId", loginResponseModel.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(204));
    }

    @Step("Добавление книги")
    public void addBook(String isbn) {
        BookDataModel bookData = new BookDataModel();
        BookDataModel.CollectionOfIsbns isbnCollection = new BookDataModel.CollectionOfIsbns();
        bookData.setUserId(userId);
        isbnCollection.setIsbn(isbn);
        bookData.setCollectionOfIsbns(Collections.singletonList(isbnCollection));

        given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponseModel.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpec(201));
    }

    @Step("Установка cookie")
    public void setCookie() {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));
    }

    }
