package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cloud Storage Integration Tests on Chrome")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

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

	@Test
	@DisplayName("Required 1A: Verifies that an unauthorized user can only access the login and signup pages.")
	public void testGetLoginAndSignUpPages() {
		assertEquals("Sign Up", driver.getTitle());
		driver.get(BASE_URL + this.port + "/login");
		assertEquals("Login", driver.getTitle());
		driver.get(BASE_URL + this.port + "/home");
		assertEquals("Login", driver.getTitle());
		driver.get(BASE_URL + this.port + "/");
		assertEquals("Login", driver.getTitle());
		driver.get(BASE_URL + this.port + "/files/delete?fileId=1");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Sign up and click continue to Login page.")
	public void testSignupAndContinue() {
		signupPage.signUp(driver,"Jill", "Doe", "jilld", "12345678");
		assertNotNull(signupPage.getSuccessMsg(driver));
		signupPage.clickContinueLink(driver);
		assertTrue(loginPage.isLoaded(driver));
	}

	@Test
	@DisplayName("Click the 'Click here to sign up' link.")
	public void testClickHereToSignUp() {
		driver.get(BASE_URL + this.port + "/login");
		loginPage.clickSignUpLink();
		assertTrue(signupPage.isLoaded(driver));
	}

	@Test
	@DisplayName("Attempt to register an existing user.")
	public void testRegisterUserExists() {
		signupPage.signUp(driver, "Pete", "Doe", "pdoe", "12345678");
		driver.get(BASE_URL + port + "/signup");
		signupPage.signUp(driver,"Pete", "Again", "pdoe", "12345678");
		assertNotNull(signupPage.getErrorMsg(driver));
	}

	@Test
	@DisplayName("Required 1B: Signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.")
	public void testLogout() {
		String username = "jlogout";
		String password = "12345678";
		signupPage.signUp("Jack", "Smoe", username, password);
		driver.get(BASE_URL + port + "/login");
		loginPage.login(driver, username, password);
		driver.get(BASE_URL + port + "/home");
		homePage.clickLogoutButton(driver);
		assertTrue(loginPage.isLoaded(driver), "User didn't log out successfully.");
		driver.get(BASE_URL + port + "/home");
		assertFalse(homePage.isLoaded(driver), "The homepage was accessed unauthorized.");
		assertTrue(loginPage.isLoaded(driver), "User was not redirected successfully.");
	}

	// Note Tests
	@Test
	@DisplayName("Required 2A: Creates a note, and verifies it is displayed.")
	public void testSaveNote() {
		String testTitle = "Test Title";
		String testDescription = "This is a test of the note broadcasting system.";
		signupPage.signUp("Joe", "Note", "note1", "12345678");
		driver.get(BASE_URL + this.port + "/login");
		loginPage.login("note1", "12345678");
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickNotesTab(driver);
		homePage.clickAddNoteButton(driver);
		homePage.saveNote(driver, testTitle, testDescription);
		boolean bSuccessMsg = resultPage.isSuccessMessage(driver);
		resultPage.clickNavLink(driver);
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickNotesTab(driver);
		String resultTitle = homePage.findOnPage(driver, testTitle);
		String resultDescription = homePage.findOnPage(driver, testDescription);
		assertAll("Save New Note",
				() -> assertTrue(bSuccessMsg, "The success message was not displayed."),
				() -> assertEquals(testTitle, resultTitle, "The new note title was not displayed"),
				() -> assertEquals(testDescription, resultDescription, "The new note description was not displayed")
		);
	}
	
	@Test
	@DisplayName("Required 2B: Edits an existing note and verifies that the changes are displayed")
	public void testEditNote() {
		String testTitle = "Test Title";
		String testDescription = "This is a test of the note broadcasting system.";
		String editTitle = "Edit";
		String editDescription = "The test took place no one was injured.";
		String expectedTitle = testTitle + editTitle;
		String expectedDescription = testDescription + editDescription;
		signupPage.signUp("Joe", "Note", "note1", "12345678");
		driver.get(BASE_URL + this.port + "/login");
		loginPage.login(driver, "note1", "12345678");
		driver.get(BASE_URL + port + "/home");
		homePage.clickNotesTab(driver);
		homePage.clickAddNoteButton(driver);
		homePage.saveNote(driver, testTitle, testDescription);
		driver.get(BASE_URL + port + "/home");
		homePage.clickNotesTab(driver);
		// get the id of the last item in the list.
		String buttonId = homePage.getLastAddedNoteEditButtonID();
		homePage.clickEditNoteButton(driver, buttonId);
		homePage.saveNote(driver, editTitle, editDescription);
		boolean bSuccessMsg = resultPage.isSuccessMessage(driver);
		resultPage.clickNavLink(driver);
		driver.get(BASE_URL + port + "/home");
		homePage.clickNotesTab(driver);
		String resultTitle = homePage.findOnPage(driver, expectedTitle);
		String resultDescription = homePage.findOnPage(driver, expectedDescription);

		assertAll("Edit Note",
				() -> assertTrue(bSuccessMsg, "The success message was not displayed."),
				() -> assertEquals(expectedTitle, resultTitle, "The edited title is incorrect."),
				() -> assertEquals(expectedDescription, resultDescription, "The edited description is incorrect.")
		);
	}

	@Test
	@DisplayName("Required 2C: Deletes a note and verifies that the note is no longer displayed")
	public void testDeleteNote() {
		String testTitle = "Test Title";
		String testDescription = "This is a test of the note broadcasting system.";
		signupPage.signUp(driver, "Joe", "Note", "delete1", "12345678");
		driver.get(BASE_URL + this.port + "/login");
		loginPage.login(driver, "delete1", "12345678");
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickNotesTab(driver);
		homePage.clickAddNoteButton(driver);
		homePage.saveNote(driver, testTitle, testDescription);
		driver.get(BASE_URL + port + "/home");
		homePage.clickNotesTab(driver);
		String resultTitle = homePage.findOnPage(driver, testTitle);
		String resultDescription = homePage.findOnPage(driver, testDescription);
		String buttonId = homePage.getLastAddedNoteDeleteButtonID();
		homePage.clickDeleteNoteButton(buttonId);
		boolean bSuccessMsg = resultPage.isSuccessMessage(driver);
		resultPage.clickNavLink(driver);
		driver.get(BASE_URL + port + "/home");
		homePage.clickNotesTab(driver);
		boolean bSuccessDelete = homePage.isAnyNoteDisplayed(driver);
		assertAll("Delete Note",
				() -> assertTrue(bSuccessMsg, "The success message was not displayed."),
				() -> assertFalse(bSuccessDelete, "The note was not deleted."),
				() -> assertEquals(testTitle, resultTitle, "The new note title was never saved."),
				() -> assertEquals(testDescription, resultDescription, "The new note description was never saved.")
		);
	}

	// Credential Test
	@Test
	@DisplayName("Required 3A: Creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.")
	public void testSaveCredential() {
		String testUrl = "http://redfish.org";
		String testUsername = "Bingo";
		String testPassword = "big2BingBing";
		signupPage.signUp(driver, "Joe", "Cred", "cred1", "12345678");
		driver.get(BASE_URL + this.port + "/login");
		loginPage.login(driver, "cred1", "12345678");
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		homePage.clickAddCredentialButton(driver);
		homePage.saveCredential(driver, testUrl, testUsername, testPassword);
		boolean bSuccessMsg = resultPage.isSuccessMessage(driver);
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		boolean bResultUrl = homePage.isOnPage(driver, testUrl);
		boolean bResultUsername = homePage.isOnPage(driver, testUsername);
		boolean bPasswordEncrypted = homePage.isNotOnPage(driver, testPassword);
		assertAll("Save New Credential",
				() -> assertTrue(bSuccessMsg, "The success message was not displayed after saving the new credential."),
				() -> assertTrue(bResultUrl, "The credential url was not displayed."),
				() -> assertTrue(bResultUsername, "The credential username was not displayed."),
				() -> assertTrue(bPasswordEncrypted,"The credential password was displayed and not encrypted.")
		);
	}

	@Test
	@DisplayName("Required 3B: Views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.")
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
		signupPage.signUp(driver, "Joe", "Cred", "cred2", "12345678");
		driver.get(BASE_URL + this.port + "/login");
		loginPage.login(driver, "cred2", "12345678");
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		homePage.clickAddCredentialButton(driver);
		homePage.saveCredential(driver, testUrl, testUsername, testPassword);
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		// get the id of the last item in the list.
		String buttonId = homePage.getLastAddedCredentialEditButtonID();
		homePage.clickEditCredentialButton(driver, buttonId);
		homePage.saveCredential(driver, editUrl, editUsername, editPassword);
		boolean bSuccessMsg = resultPage.isSuccessMessage(driver);
		resultPage.clickNavLink(driver);
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		boolean bResultUrl = homePage.isOnPage(driver, expectedUrl);
		boolean bResultUsername = homePage.isOnPage(driver, expectedUsername);
		homePage.clickEditCredentialButton(driver, buttonId);
		String resultPassword = homePage.getDecryptedPassword(driver);
		assertAll("Edit Credential",
				() -> assertTrue(bSuccessMsg, "The success message was not displayed after the edit."),
				() -> assertTrue(bResultUrl, "The edited url is incorrect."),
				() -> assertTrue(bResultUsername, "The edited username is incorrect."),
				() -> assertEquals(expectedPassword, resultPassword, "The edited password is incorrect or not decrypted.")
		);
	}

	@Test
	@DisplayName("Required 3C: Deletes an existing set of credentials and verifies that the credentials are no longer displayed.")
	public void testDeleteCredential() {
		String testUrl = "http://deleteme.info";
		String testUsername = "Goner12";
		String testPassword = "zAPMe-n0w";
		signupPage.signUp(driver, "Joe", "Cred", "jcred3", "12345678");
		driver.get(BASE_URL + this.port + "/login");
		loginPage.login(driver, "jcred3", "12345678");
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		homePage.clickAddCredentialButton(driver);
		homePage.saveCredential(driver, testUrl, testUsername, testPassword);
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		// get the id of the last item in the list.
		boolean bCredentialSaved = homePage.isOnPage(driver, testUrl);
		String buttonId = homePage.getLastAddedCredentialDeleteButtonID();
		homePage.clickDeleteCredentialButton(driver, buttonId);
		boolean bSuccessMsg = resultPage.isSuccessMessage(driver);
		driver.get(BASE_URL + this.port + "/home");
		homePage.clickCredentialsTab(driver);
		boolean bSuccessDelete = homePage.isAnyCredentialDisplayed(driver);
		assertAll("Delete Credential",
				() -> assertTrue(bCredentialSaved, "The credential was never save for later deletion."),
				() -> assertTrue(bSuccessMsg, "The success message was not displayed after delete."),
				() -> assertFalse(bSuccessDelete, "The credential was not deleted.")
		);
	}
}
