package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "msg-logged-out")
    private WebElement msgLoggedOut;

    @FindBy(id = "msg-invalid")
    private WebElement msgInvalid;

    @FindBy(id = "sign-up-link")
    private WebElement signUpLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(WebDriver driver, String username, String password) {
        /* @TODO Find a WebDriverWait method to make the login page wait to load completely.
         * Tried to use a WebDriverWait, but not matter what I waited for nothing paused the page enough.
         * The problem is that the inputUsername field is not loading before the .sendKeys() method executes.
         *
         * Tried this:
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));

         * also tried this variation and others similar:
        WebElement elemUsername = wait.until(ExpectedConditions.elementToBeClickable(inputUsername));
        elemUsername.sendKeys(username);

*        * The Thread.sleep method works every time and the tests succeed.
        */
        try {Thread.sleep(2000);} catch (Exception e){System.out.println("LoginPage.login(): " + e.getMessage());}
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public boolean isLoggedOut() {
        return msgLoggedOut != null;
    }

    public boolean isInvalidUserIdOrPassword() {
        return msgInvalid != null;
    }

    public void clickSignUpLink() {
        signUpLink.click();
    }

    public boolean isPageLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        return btn != null;
    }

}
