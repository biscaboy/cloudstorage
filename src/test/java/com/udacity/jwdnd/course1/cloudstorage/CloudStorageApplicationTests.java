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
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static String BASE_URL = "http://localhost:";

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
		signupPage.signUp("John", "Doe", "jdoe", "12345678");
		assertNotNull(signupPage.getSuccessMsg(driver));
	}

	@Test
	@DisplayName("Click the back to login link")
	public void testClickBackLink() {
		signupPage.clickGoBackLink();
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Sign up and click continue to Login page.")
	public void testSignupAndContinue() {
		signupPage.signUp("Jill", "Doe", "jilld", "12345678");
		try {Thread.sleep(2000);} catch (Exception e){System.out.println(e.getMessage());}
		assertNotNull(signupPage.getSuccessMsg(driver));
		signupPage.clickContinueLink();
		try {Thread.sleep(2000);} catch (Exception e){System.out.println(e.getMessage());}
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Login a signed up user.")
	public void testLogin() {
		String username = "jackd";
		String password = "12345678";
		signupPage.signUp("Jack", "Doe", username, password);
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
		// calls to the wait.until methods on the Page classes are not reliable/do not work. Commenting out for later investigation.
		//assertTrue(signupPage.waitUntilLoaded(driver), "The signup page did not load after submission.");
		signupPage.clickContinueLink();
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
		assertEquals("Login", driver.getTitle());
		//assertEquals(loginPage.waitUntilLoaded(driver), "Failed to redirect to the login page.");
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
		loginPage.login(driver, username, password);
		//assertTrue(homePage.waitUntilLoaded(driver), "Failed to redirect to the home page.");
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
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
		String password = "1234";
		driver.get(BASE_URL + port + "/login");
		loginPage.login(driver, username, password);
		assertTrue(loginPage.isInvalidUserIdOrPassword());
	}

	@Test
	@DisplayName("Logout a user.")
	public void testLogout() {
		String username = "jlogout";
		String password = "12345678";
		signupPage.signUp("Jack", "Smoe", username, password);
		// calls to the wait.until methods on the Page classes are not reliable/do not work. Commenting out for later investigation.
		//signupPage.waitUntilLoaded(driver);
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
		signupPage.clickContinueLink();
		//assertTrue(loginPage.waitUntilLoaded(driver), "Failed to redirect to login page from signup.");
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
		assertEquals("Login", driver.getTitle());
		loginPage.login(driver, username, password);
		//assertTrue(homePage.waitUntilLoaded(driver), "Failed to redirect to the home page.");
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
		assertEquals("Home", driver.getTitle());
		homePage.clickLogoutButton();
		//assertTrue(loginPage.waitUntilLoaded(driver), "Failed to redirect to the login page after logout.");
		try {Thread.sleep(1000);} catch (Exception e){System.out.println(e.getMessage());}
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
			loginPage.login(driver, "note1", "12345678");
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
			signupPage.signUp("Joe", "Note", "note1", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "note1", "12345678");
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
			signupPage.signUp("Joe", "Note", "delete1", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "delete1", "12345678");
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
		String encryptedTestPassword = "";
		boolean bResultUrl = false;
		boolean bResultUsername = false;
		String resultPassword = null;
		boolean bSuccessMsg = false;
		boolean bPasswordEncrypted = true;
		try {
			signupPage.signUp("Joe", "Cred", "cred1", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "cred1", "12345678");
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
			signupPage.signUp("Joe", "Cred", "jcred2", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "jcred2", "12345678");
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
			signupPage.signUp("Joe", "Cred", "jcred3", "12345678");
			driver.get(BASE_URL + this.port + "/login");
			loginPage.login(driver, "jcred3", "12345678");
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
