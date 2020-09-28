package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialIntegrationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private static final String BASE_URL = "http://localhost:";

    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private ResultPage resultPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        driver.get(BASE_URL + port + "/signup");
        signupPage = new SignupPage(driver);
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        resultPage = new ResultPage(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }
    // Note Tests
    @Test
    @DisplayName("Save a new credential.")
    public void testSaveCredential() {
        String testUrl = "http://redfish.org";
        String testUsername = "Bingo";
        String testPassword = "big2BingBing";
        String encryptedTestPassword = "";
        boolean bResultUrl = false;
        boolean bResultUsername = false;
        String resultPassword = null;
        boolean bSuccessMsg = false;
        boolean bPasswordEncrypted = true;
        try {
            signupPage.signUp("Joe", "Cred", "cred1", "1234");
            driver.get(BASE_URL + this.port + "/login");
            loginPage.login(driver, "cred1", "1234");
            Thread.sleep(1000);
            homePage.clickCredentailsTab();
            Thread.sleep(1000);
            homePage.clickAddCredentialButton();
            Thread.sleep(1000);
            homePage.saveCredential(testUrl, testUsername, testPassword);
            Thread.sleep(1000);
            bSuccessMsg = resultPage.isSuccessMessage();
            resultPage.clickNavLink();
            Thread.sleep(1000);
            bResultUrl = driver.findElement(By.xpath("//*[text()='" + testUrl + "']")).isDisplayed();
            bResultUsername = driver.findElement(By.xpath("//*[text()='" + testUsername + "']")).isDisplayed();
            try {
                bPasswordEncrypted = driver.findElement(By.xpath("//*[text()='" + testPassword + "']")).isDisplayed();
            } catch (NoSuchElementException e) {
                bPasswordEncrypted = false;
            }
            String buttonId = homePage.getLastAddedCredentialEditButtonID();
            homePage.clickEditCredentialButton(buttonId);
            resultPassword = homePage.getDecryptedPassword();
        } catch (Exception e) {
            fail("Exception in testSaveCredential: " + e.getMessage());
        }
        assertTrue(bSuccessMsg, "The success message was not displayed after saving the new credential.");
        assertTrue(bResultUrl, "The credential url was not displayed.");
        assertTrue(bResultUsername, "The credential username was not displayed.");
        assertFalse(bPasswordEncrypted,"The credential password was displayed and not encrypted.");
        assertEquals(testPassword, resultPassword,"The credential password was not decrypted.");
    }

    @Test
    @DisplayName("Edit a credential.")
    public void testEditCredential() {
        String testUrl = "http://freaknawesome.us";
        String testUsername = "Freak1";
        String testPassword = "SNeekee234";
        String editUrl = ".com";
        String editUsername = "23";
        String editPassword = "567";
        String expectedUrl = testUrl + editUrl;
        String expectedUsername = testUsername + editUsername;
        String expectedPassword = testPassword + editPassword;
        boolean bResultUrl = false;
        boolean bResultUsername = false;
        String resultPassword = null;
        boolean bSuccessMsg = false;
        try {
            signupPage.signUp("Joe", "Cred", "jcred2", "1234");
            driver.get(BASE_URL + this.port + "/login");
            loginPage.login(driver, "jcred2", "1234");
            Thread.sleep(1000);
            homePage.clickCredentailsTab();
            Thread.sleep(1000);
            homePage.clickAddCredentialButton();
            Thread.sleep(1000);
            homePage.saveCredential(testUrl, testUsername, testPassword);
            Thread.sleep(1000);
            resultPage.clickNavLink();
            // get the id of the last item in the list.
            Thread.sleep(1000);
            String buttonId = homePage.getLastAddedCredentialEditButtonID();
            homePage.clickEditCredentialButton(buttonId);
            Thread.sleep(1000);
            homePage.saveCredential(editUrl, editUsername, editPassword);
            Thread.sleep(1000);
            bSuccessMsg = resultPage.isSuccessMessage();
            resultPage.clickNavLink();
            Thread.sleep(1000);
            bResultUrl = driver.findElement(By.xpath("//*[text()='" + expectedUrl + "']")).isDisplayed();
            bResultUsername = driver.findElement(By.xpath("//*[text()='" + expectedUsername + "']")).isDisplayed();
            homePage.clickEditCredentialButton(buttonId);
            Thread.sleep(1000);
            resultPassword = homePage.getDecryptedPassword();
        } catch (Exception e) {
            fail("Exception in testEditCredential: " + e.getMessage());
        }
        assertTrue(bSuccessMsg, "The success message was not displayed after the edit.");
        assertTrue(bResultUrl, "The edited url is incorrect.");
        assertTrue(bResultUsername, "The edited username is incorrect.");
        assertEquals(expectedPassword, resultPassword, "The edited password is incorrect.");
    }

    @Test
    @DisplayName("Delete a credential.")
    public void testDeleteCredentail() {
        String testUrl = "http://deleteme.info";
        String testUsername = "Goner12";
        String testPassword = "zAPMe-n0w";
        boolean bSuccessMsg = false;
        boolean bSuccessDelete = false;
        try {
            signupPage.signUp("Joe", "Cred", "jcred3", "1234");
            driver.get(BASE_URL + this.port + "/login");
            loginPage.login(driver, "jcred3", "1234");
            Thread.sleep(1000);
            homePage.clickCredentailsTab();
            Thread.sleep(1000);
            homePage.clickAddCredentialButton();
            Thread.sleep(1000);
            homePage.saveCredential(testUrl, testUsername, testPassword);
            Thread.sleep(1000);
            resultPage.clickNavLink();
            // get the id of the last item in the list.
            Thread.sleep(1000);
            String buttonId = homePage.getLastAddedCredentialDeleteButtonID();
            homePage.clickDeleteCredentialButton(buttonId);
            Thread.sleep(1000);
            bSuccessMsg = resultPage.isSuccessMessage();
            resultPage.clickNavLink();
            Thread.sleep(1000);
            bSuccessDelete = homePage.isAnyCredentialDisplayed();
        } catch (Exception e) {
            fail("Exception in testDeleteCredential: " + e.getMessage());
        }
        assertTrue(bSuccessMsg, "The success message was not displayed after delete.");
        assertFalse(bSuccessDelete, "The credential was not deleted.");
    }

}
