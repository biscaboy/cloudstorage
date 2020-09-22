package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


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

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
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
}
