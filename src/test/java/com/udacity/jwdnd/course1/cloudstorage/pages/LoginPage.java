package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends WaitablePage {

    private static final String INPUT_USERNAME = "inputUsername";
    @FindBy(id = INPUT_USERNAME)
    private WebElement inputUsername;

    private static final String INPUT_PASSWORD = "inputPassword";
    @FindBy(id = INPUT_PASSWORD)
    private WebElement inputPassword;

    private static final String SUBMIT_BUTTON = "submit-button";
    @FindBy(id = SUBMIT_BUTTON)
    private WebElement submitButton;

    private static final String MESSAGE_LOGGED_OUT = "msg-logged-out";
    @FindBy(id = MESSAGE_LOGGED_OUT)
    private WebElement msgLoggedOut;

    private static final String MESSAGE_INVALID = "msg-invalid";
    @FindBy(id = MESSAGE_INVALID)
    private WebElement msgInvalid;

    private static final String SIGN_UP_LINK = "sign-up-link";
    @FindBy(id = SIGN_UP_LINK)
    private WebElement signUpLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(WebDriver driver, String username, String password) {
        waitForElement(driver, INPUT_USERNAME).sendKeys(username);
        waitForElement(driver, INPUT_PASSWORD).sendKeys(password);
        waitForElement(driver, SUBMIT_BUTTON).click();
    }

    public void login(String username, String password) {
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public boolean isInvalidUserIdOrPassword(WebDriver driver) {
        return isElementDisplayed(driver, MESSAGE_INVALID);
    }

    public boolean isInvalidUserIdOrPassword() {
        String msg = msgInvalid.getTagName();
        return msg != null;
    }

    public void clickSignUpLink(WebDriver driver) {
        waitForElement(driver, SIGN_UP_LINK).click();
    }

    public void clickSignUpLink() {
        signUpLink.click();
    }

    public boolean isLoaded(WebDriver driver) {
        return isElementDisplayed(driver, SUBMIT_BUTTON);
    }

}
