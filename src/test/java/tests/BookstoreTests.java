package tests;

import api.RestApi;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

public class BookstoreTests extends TestBase {
    RestApi restApi = new RestApi();
    ProfilePage profilePage = new ProfilePage();

    @Test
    void deleteBookTest() {
        restApi.authApiPostRequest();
        restApi.deleteAllBooks();
        restApi.addBook("9781449325862");
        restApi.setCookie();
        profilePage.openProfilePage();
        profilePage.checkUserName();
        profilePage.checkBookInProfile();
        profilePage.deleteBookFromProfile();
        profilePage.checkBookWasRemovedFromProfile();
    }
}
