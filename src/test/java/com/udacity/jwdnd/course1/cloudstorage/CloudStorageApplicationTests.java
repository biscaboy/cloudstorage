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
class CloudStorageApplicationTests {

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


	@Test
	@DisplayName("Display login page.")
	public void getLoginPage() {
		driver.get(baseURL + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	//@todo add a logout test

	@Test
	@DisplayName("Sign up a new user.")
	public void testRegisterUser() {
		signupPage.signUp("John", "Doe", "jdoe", "1234");
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
		signupPage.signUp("Jill", "Doe", "jilld", "1234");
		try {Thread.sleep(2000);} catch (Exception e){System.out.println(e.getMessage());}
		assertNotNull(signupPage.getSuccessMsg(driver));
		signupPage.clickContinueLink(driver);
		try {Thread.sleep(2000);} catch (Exception e){System.out.println(e.getMessage());}
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Login a signed up user.")
	public void testLogin() {
		String username = "jackd";
		String password = "1234";
		signupPage.signUp("Jack", "Doe", username, password);
		try {Thread.sleep(2000);} catch (Exception e){System.out.println(e.getMessage());}
		signupPage.clickContinueLink(driver);
		assertEquals("Login", driver.getTitle());
		loginPage.login(driver, username, password);
		assertTrue(homePage.isPageLoaded(driver), "Failed to redirect to the home page.");
	}

	@Test
	@DisplayName("Click the 'Click here to sign up' link.")
	public void testClickHereToSignUp() {
		driver.get(baseURL + this.port + "/login");
		loginPage.clickSignUpLink();
		assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@DisplayName("Register an existing user.")
	public void testRegisterUserExists() {
		signupPage.signUp("John", "Doe", "jdoe", "1234");
		signupPage.signUp("John", "Doe", "jdoe", "1234");
		assertNotNull(signupPage.getErrorMsg());
	}

	@Test
	@DisplayName("Attempt to login without signing up.")
	public void testNonAuthenticatedLogin() {
		String username = "jackd";
		String password = "1234";
		driver.get(baseURL + port + "/login");
		loginPage.login(driver, username, password);
		assertTrue(loginPage.isInvalidUserIdOrPassword());
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

	// @todo Finish this test!
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
			Thread.sleep(1000);
			homePage.clickEditNoteButton(1);
			Thread.sleep(1000);
			homePage.saveNote(editTitle, editDescription);
			Thread.sleep(1000);
			bSuccessMsg = resultPage.isSuccessMessage();
			Thread.sleep(1000);
			resultPage.clickNavLink();
			Thread.sleep(1000);
			resultTitle = driver.findElement(By.xpath("//*[text()='" + expectedTitle + "']")).getText();
			resultDescription = driver.findElement(By.xpath("//*[text()='" + expectedDescription + "']")).getText();
		} catch (Exception e) {
			fail("Exception in testEditNote: " + e.getMessage() + " - Stack:" + e.getStackTrace().toString());
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
			Thread.sleep(1000);
			homePage.clickDeleteNoteButton(1);
			Thread.sleep(1000);
			bSuccessMsg = resultPage.isSuccessMessage();
			Thread.sleep(1000);
			resultPage.clickNavLink();
			Thread.sleep(1000);
			bSuccessDelete = homePage.isAnyNotes();
		} catch (Exception e) {
			fail("Exception in testSaveNote: " + e.getMessage());
		}
		assertTrue(bSuccessMsg, "The success message was not displayed.");
		assertTrue(bSuccessDelete, "The note (id 1) was not deleted.");
		assertEquals(testTitle, resultTitle, "The new note title was never saved.");
		assertEquals(testDescription, resultDescription, "The new note description was never saved.");
	}

	//@todo add a test that adds several notes then deletes one.

}
