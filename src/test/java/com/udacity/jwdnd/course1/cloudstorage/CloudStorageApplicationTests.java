package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	//private WebDriverWait wait;
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
		//this.wait = new WebDriverWait(driver, 20);
		//driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
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
	@DisplayName("Attempt to unauthorized access.")
	public void testUnauthorizedAccess() {
		driver.get(BASE_URL + this.port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Display login page.")
	public void testGetLoginPage() {
		driver.get(BASE_URL + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	//@todo add a logout test

	@Test
	@DisplayName("Sign up a new user.")
	public void testRegisterUser() {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signUp(wait,"John", "Doe", "jdoe", "12345678");
		assertNotNull(signupPage.getSuccessMsg(wait));
	}

	@Test
	@DisplayName("Click the back to login link")
	public void testClickBackLink() {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SignupPage signupPage = new SignupPage(driver);
		signupPage.clickGoBackLink(wait);
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Sign up and click continue to Login page.")
	public void testSignupAndContinue() {
		signupPage.signUp("Jill", "Doe", "jilld", "12345678");
		assertNotNull(signupPage.getSuccessMsg());
		signupPage.clickContinueLink(driver);
		try{Thread.sleep(1000);}catch(Exception e){};
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Login a signed up user.")
	public void testLogin() {
		String username = "jackd";
		String password = "12345678";

		signupPage.signUp("Jack", "Doe", username, password);

		driver.get(BASE_URL + port + "/login");

		loginPage.login(username, password);

		driver.get(BASE_URL + port + "/home");

		assertEquals("Home", driver.getTitle());
	}

	@Test
	@DisplayName("Click the 'Click here to sign up' link.")
	public void testClickHereToSignUp() {
		driver.get(BASE_URL + this.port + "/login");
		loginPage.clickSignUpLink();
		assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@DisplayName("Register an existing user.")
	public void testRegisterUserExists() {
		signupPage.signUp("John", "Doe", "jdoe", "12345678");
		signupPage.signUp("John", "Doe", "jdoe", "12345678");
		assertNotNull(signupPage.getErrorMsg());
	}

	@Test
	@DisplayName("Attempt to login without signing up.")
	public void testNonAuthenticatedLogin() {
		String username = "jackd";
		String password = "12341234";
		driver.get(BASE_URL + port + "/login");
		loginPage.login(username, password);
		assertTrue(loginPage.isInvalidUserIdOrPassword());
	}

	@Test
	@DisplayName("Logout a user.")
	public void testLogout() {
		String username = "jlogout";
		String password = "12345678";
		signupPage.signUp("Jack", "Smoe", username, password);
		driver.get(BASE_URL + port + "/login");
		loginPage.login(username, password);
		driver.get(BASE_URL + port + "/home");
		homePage.clickLogoutButton();
		assertEquals("Login", driver.getTitle());
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
			signupPage.signUp("Joe", "Note", "note1", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login("note1", "12345678");
			driver.get(BASE_URL + this.port + "/home");
			homePage.clickNotesTab(driver);
			homePage.clickAddNoteButton(driver);
			homePage.saveNote(driver, testTitle, testDescription);
			bSuccessMsg = resultPage.isSuccessMessage(driver);
			resultPage.clickNavLink(driver);
			driver.get(BASE_URL + this.port + "/home");
			homePage.clickNotesTab(driver);
			WebElement titleEl = new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + testTitle + "']"))));
			WebElement descEl = new WebDriverWait(driver, 10)
					.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='" + testDescription + "']"))));
			resultTitle = titleEl.getText();
			resultDescription = descEl.getText();
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
			signupPage.signUp("Joe", "Note", "note1", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "note1", "12345678");
			driver.get(BASE_URL + port + "/home");
			homePage.clickNotesTab(driver);
			homePage.clickAddNoteButton(driver);
			homePage.saveNote(driver, testTitle, testDescription);
			Thread.sleep(1000);
			driver.get(BASE_URL + port + "/home");
			Thread.sleep(1000);
			homePage.clickNotesTab();
			// get the id of the last item in the list.
			Thread.sleep(1000);
			String buttonId = homePage.getLastAddedNoteEditButtonID();
			Thread.sleep(1000);
			homePage.clickEditNoteButton(buttonId);
			homePage.saveNote(driver, editTitle, editDescription);
			bSuccessMsg = resultPage.isSuccessMessage(driver);
			resultPage.clickNavLink(driver);
			Thread.sleep(1000);
			driver.get(BASE_URL + port + "/home");
			Thread.sleep(1000);
			homePage.clickNotesTab(driver);
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
			WebDriverWait wait = new WebDriverWait(driver, 100);
			SignupPage signupPage = new SignupPage(driver);
			signupPage.signUp(wait, "Joe", "Note", "delete1", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "delete1", "12345678");
			Thread.sleep(1000);
			homePage.clickNotesTab();
			Thread.sleep(1000);
			homePage.clickAddNoteButton();
			Thread.sleep(1000);
			homePage.saveNote(testTitle, testDescription);
			Thread.sleep(1000);
			resultPage.clickNavLink(driver);
			Thread.sleep(1000);
			resultTitle = driver.findElement(By.xpath("//*[text()='" + testTitle + "']")).getText();
			resultDescription = driver.findElement(By.xpath("//*[text()='" + testDescription + "']")).getText();
			String buttonId = homePage.getLastAddedNoteDeleteButtonID();
			homePage.clickDeleteNoteButton(buttonId);
			Thread.sleep(1000);
			bSuccessMsg = resultPage.isSuccessMessage();
			resultPage.clickNavLink(driver);
			Thread.sleep(1000);
			bSuccessDelete = homePage.isAnyNoteDisplayed();
		} catch (Exception e) {
			fail("Exception in testSaveNote: " + e.getMessage());
		}
		assertTrue(bSuccessMsg, "The success message was not displayed.");
		assertFalse(bSuccessDelete, "The note was not deleted.");
		assertEquals(testTitle, resultTitle, "The new note title was never saved.");
		assertEquals(testDescription, resultDescription, "The new note description was never saved.");
	}

	// Credential Test
	@Test
	@DisplayName("Save a new credential.")
	public void testSaveCredential() {
		String testUrl = "http://redfish.org";
		String testUsername = "Bingo";
		String testPassword = "big2BingBing";
		boolean bResultUrl = false;
		boolean bResultUsername = false;
		String resultPassword = null;
		boolean bSuccessMsg = false;
		boolean bPasswordEncrypted = true;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SignupPage signupPage = new SignupPage(driver);
		try {
			signupPage.signUp(wait, "Joe", "Cred", "cred1", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "cred1", "12345678");
			Thread.sleep(1000);
			homePage.clickCredentialsTab();
			Thread.sleep(1000);
			homePage.clickAddCredentialButton();
			Thread.sleep(1000);
			homePage.saveCredential(driver, testUrl, testUsername, testPassword);
			Thread.sleep(1000);
			bSuccessMsg = resultPage.isSuccessMessage();
			resultPage.clickNavLink(driver);
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
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SignupPage signupPage = new SignupPage(driver);
		try {
			signupPage.signUp(wait, "Joe", "Cred", "jcred2", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "jcred2", "12345678");
			Thread.sleep(1000);
			homePage.clickCredentialsTab();
			Thread.sleep(1000);
			homePage.clickAddCredentialButton();
			Thread.sleep(1000);
			homePage.saveCredential(driver, testUrl, testUsername, testPassword);
			Thread.sleep(1000);
			resultPage.clickNavLink(driver);
			// get the id of the last item in the list.
			Thread.sleep(1000);
			String buttonId = homePage.getLastAddedCredentialEditButtonID();
			homePage.clickEditCredentialButton(buttonId);
			Thread.sleep(1000);
			homePage.saveCredential(driver, editUrl, editUsername, editPassword);
			Thread.sleep(1000);
			bSuccessMsg = resultPage.isSuccessMessage();
			resultPage.clickNavLink(driver);
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
	public void testDeleteCredential() {
		String testUrl = "http://deleteme.info";
		String testUsername = "Goner12";
		String testPassword = "zAPMe-n0w";
		boolean bSuccessMsg = false;
		boolean bSuccessDelete = false;
		WebDriverWait wait = new WebDriverWait(driver, 100);
		SignupPage signupPage = new SignupPage(driver);
		try {
			signupPage.signUp(wait, "Joe", "Cred", "jcred3", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "jcred3", "12345678");
			Thread.sleep(1000);
			homePage.clickCredentialsTab();
			Thread.sleep(1000);
			homePage.clickAddCredentialButton();
			Thread.sleep(1000);
			homePage.saveCredential(driver, testUrl, testUsername, testPassword);
			Thread.sleep(1000);
			resultPage.clickNavLink(driver);
			// get the id of the last item in the list.
			Thread.sleep(1000);
			String buttonId = homePage.getLastAddedCredentialDeleteButtonID();
			homePage.clickDeleteCredentialButton(buttonId);
			Thread.sleep(1000);
			bSuccessMsg = resultPage.isSuccessMessage();
			resultPage.clickNavLink(driver);
			Thread.sleep(1000);
			bSuccessDelete = homePage.isAnyCredentialDisplayed();
		} catch (Exception e) {
			fail("Exception in testDeleteCredential: " + e.getMessage());
		}
		assertTrue(bSuccessMsg, "The success message was not displayed after delete.");
		assertFalse(bSuccessDelete, "The credential was not deleted.");
	}
}
