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
	@DisplayName("Attempt to unauthorized access.")
	public void testUnauthorizedAccess() {
		driver.get(baseURL + this.port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Display login page.")
	public void testGetLoginPage() {
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

	@Test
	@DisplayName("Logout a user.")
	public void testLogout() {
		String username = "jsmoe";
		String password = "1234";
		signupPage.signUp("Jack", "Smoe", username, password);
		try {Thread.sleep(2000);} catch (Exception e){System.out.println(e.getMessage());}
		signupPage.clickContinueLink(driver);
		assertEquals("Login", driver.getTitle());
		loginPage.login(driver, username, password);
		assertTrue(homePage.isPageLoaded(driver), "Failed to redirect to the home page.");
		homePage.clickLogoutButton();
		assertEquals("Login", driver.getTitle());
	}
}
