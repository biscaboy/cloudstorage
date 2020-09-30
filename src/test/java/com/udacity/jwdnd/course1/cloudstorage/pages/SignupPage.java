package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "cont-to-login")
    private WebElement continueLink;

    @FindBy(id = "back-to-login")
    private WebElement backLink;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password){
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public String getSuccessMsg(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 2);
        return wait.until(ExpectedConditions.elementToBeClickable(continueLink)).getText();
    }

    public String getErrorMsg(){
        return errorMsg.getText();
    }

    public void clickContinueLink() {
        continueLink.click();
    }

    public void clickGoBackLink() {
        backLink.click();
    }

    public boolean waitUntilLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        return btn != null;
    }
}

