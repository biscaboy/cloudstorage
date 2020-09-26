package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteIntegrationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private static String baseURL = "http://localhost:";

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
        driver.get(baseURL + port + "/signup");
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
    @DisplayName("Save a new note.")
    public void testSaveNote() {
        String testTitle = "Test Title";
        String testDescription = "This is a test of the note broadcasting system.";
        String resultTitle = null;
        String resultDescription = null;
        boolean bSuccessMsg = false;
        try {
            signupPage.signUp("Joe", "Note", "note1", "1234");
            driver.get(baseURL + this.port + "/login");
            loginPage.login(driver, "note1", "1234");
            Thread.sleep(1000);
            homePage.clickNotesTab();
            Thread.sleep(1000);
            homePage.clickAddNoteButton();
            Thread.sleep(1000);
            homePage.saveNote(testTitle, testDescription);
            Thread.sleep(1000);
            bSuccessMsg = resultPage.isSuccessMessage();
            resultPage.clickNavLink();
            Thread.sleep(1000);
            resultTitle = driver.findElement(By.xpath("//*[text()='" + testTitle + "']")).getText();
            resultDescription = driver.findElement(By.xpath("//*[text()='" + testDescription + "']")).getText();
        } catch (Exception e) {
            fail("Exception in testSaveNote: " + e.getMessage());
        }
        assertTrue(bSuccessMsg, "The success message was not displayed.");
        assertEquals(testTitle, resultTitle, "The new note title was not displayed");
        assertEquals(testDescription, resultDescription, "The new note description was not displayed");
    }

    // @TODO Finish this test!
    @Test
    @DisplayName("Edit an existing note.")
    public void testEditNote() {
        String testTitle = "Test Title";
        String testDescription = "This is a test of the note broadcasting system.";
        String editTitle = "Edit";
        String editDescription = "The test took place no one was injured.";
        String expectedTitle = testTitle + editTitle;
        String expectedDescription = testDescription + editDescription;
        String resultTitle = null;
        String resultDescription = null;
        boolean bSuccessMsg = false;
        try {
            signupPage.signUp("Joe", "Note", "note1", "1234");
            driver.get(baseURL + this.port + "/login");
            loginPage.login(driver, "note1", "1234");
            Thread.sleep(1000);
            homePage.clickNotesTab();
            Thread.sleep(1000);
            homePage.clickAddNoteButton();
            Thread.sleep(1000);
            homePage.saveNote(testTitle, testDescription);
            Thread.sleep(1000);
            resultPage.clickNavLink();
            // get the id of the last item in the list.
            Thread.sleep(1000);
            String buttonId = homePage.getLastAddedNoteEditButtonID();
            homePage.clickEditNoteButton(buttonId);
            Thread.sleep(1000);
            homePage.saveNote(editTitle, editDescription);
            Thread.sleep(1000);
            bSuccessMsg = resultPage.isSuccessMessage();
            resultPage.clickNavLink();
            Thread.sleep(1000);
            resultTitle = driver.findElement(By.xpath("//*[text()='" + expectedTitle + "']")).getText();
            resultDescription = driver.findElement(By.xpath("//*[text()='" + expectedDescription + "']")).getText();
        } catch (Exception e) {
            fail("Exception in testEditNote: " + e.getMessage());
        }
        assertTrue(bSuccessMsg, "The success message was not displayed.");
        assertEquals(expectedTitle, resultTitle, "The edited title is incorrect.");
        assertEquals(expectedDescription, resultDescription, "The edited description is incorrect.");
    }

    @Test
    @DisplayName("Delete a note.")
    public void testDeleteNote() {
        String testTitle = "Test Title";
        String testDescription = "This is a test of the note broadcasting system.";
        String resultTitle = null;
        String resultDescription = null;
        boolean bSuccessMsg = false;
        boolean bSuccessDelete = false;
        try {
            signupPage.signUp("Joe", "Note", "delete1", "1234");
            driver.get(baseURL + this.port + "/login");
            loginPage.login(driver, "delete1", "1234");
            Thread.sleep(1000);
            homePage.clickNotesTab();
            Thread.sleep(1000);
            homePage.clickAddNoteButton();
            Thread.sleep(1000);
            homePage.saveNote(testTitle, testDescription);
            Thread.sleep(1000);
            resultPage.clickNavLink();
            Thread.sleep(1000);
            resultTitle = driver.findElement(By.xpath("//*[text()='" + testTitle + "']")).getText();
            resultDescription = driver.findElement(By.xpath("//*[text()='" + testDescription + "']")).getText();
            String buttonId = homePage.getLastAddedNoteDeleteButtonID();
            homePage.clickDeleteNoteButton(buttonId);
            Thread.sleep(1000);
            bSuccessMsg = resultPage.isSuccessMessage();
            resultPage.clickNavLink();
            Thread.sleep(1000);
            bSuccessDelete = homePage.isAnyNotes();
        } catch (Exception e) {
            fail("Exception in testSaveNote: " + e.getMessage());
        }
        assertTrue(bSuccessMsg, "The success message was not displayed.");
        assertTrue(bSuccessDelete, "The note was not deleted.");
        assertEquals(testTitle, resultTitle, "The new note title was never saved.");
        assertEquals(testDescription, resultDescription, "The new note description was never saved.");
    }

    //@todo add a test that adds several notes/deletes notes for two different users.

}
