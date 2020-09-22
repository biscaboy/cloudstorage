package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static String baseURL = "http://localhost:";

	private SignupPage signupPage;
	private LoginPage loginPage;

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
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@DisplayName("Sign up a new user.")
	public void testRegisterUser() {
		signupPage.signUp("John", "Doe", "jdoe", "1234");
		assertNotNull(signupPage.getSuccessMsg());
	}

	@Test
	@DisplayName("Login a signed up user.")
	public void testLogin() {
		String username = "jackd";
		String password = "1234";
		signupPage.signUp("Jack", "Doe", username, password);
		driver.get(baseURL + port + "/login");
		loginPage.login(username, password);
		driver.get(baseURL + port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
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
		loginPage.login(username, password);
		Assertions.assertTrue(loginPage.isInvalidUserIdOrPassword());
	}
}
