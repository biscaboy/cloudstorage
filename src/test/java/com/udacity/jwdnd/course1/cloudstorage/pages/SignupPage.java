package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage extends WaitablePage {

    private static final String INPUT_FIRST_NAME = "inputFirstName";
    @FindBy(id = INPUT_FIRST_NAME)
    private WebElement inputFirstName;

    private static final String INPUT_LAST_NAME = "inputLastName";
    @FindBy(id = INPUT_LAST_NAME)
    private WebElement inputLastName;

    private static final String INPUT_USERNAME = "inputUsername";
    @FindBy(id = INPUT_USERNAME)
    private WebElement inputUsername;

    private static final String INPUT_PASSWORD = "inputPassword";
    @FindBy(id = INPUT_PASSWORD)
    private WebElement inputPassword;

    private static final String BUTTON_SUBMIT = "submit-button";
    @FindBy(id = BUTTON_SUBMIT)
    private WebElement submitButton;

    private static final String SUCCESS_MESSAGE = "success-msg";
    @FindBy(id = SUCCESS_MESSAGE)
    private WebElement successMsg;

    private static final String ERROR_MESSAGE = "error-msg";
    @FindBy(id = ERROR_MESSAGE)
    private WebElement errorMsg;

    private static final String LINK_BACK_TO_LOGIN = "back-to-login";
    @FindBy(id = LINK_BACK_TO_LOGIN)
    private WebElement backLink;

    private static final String LINK_CONTINUE_TO_LOGIN = "cont-to-login";
    @FindBy(id = LINK_CONTINUE_TO_LOGIN)
    private WebElement continueLink;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signUp(WebDriver driver, String firstName, String lastName, String username, String password){
        waitForElement(driver, INPUT_FIRST_NAME).sendKeys(firstName);
        waitForElement(driver, INPUT_LAST_NAME).sendKeys(lastName);
        waitForElement(driver, INPUT_USERNAME).sendKeys(username);
        waitForElement(driver, INPUT_PASSWORD).sendKeys(password);
        waitForElement(driver, BUTTON_SUBMIT).click();
    }

    public void signUp(String firstName, String lastName, String username, String password){
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        submitButton.click();
    }

    public String getSuccessMsg(WebDriver driver){
        return waitForElement(driver, SUCCESS_MESSAGE).getText();
    }

    public String getSuccessMsg(){
        return successMsg.getText();
    }

    public String getErrorMsg(WebDriver driver){
        return waitForElement(driver, ERROR_MESSAGE).getText();
    }

    public String getErrorMsg(){
        return errorMsg.getText();
    }

    public void clickContinueLink(WebDriver driver) {
        waitForElement(driver, LINK_CONTINUE_TO_LOGIN).click();
    }

    public void clickContinueLink() {
        continueLink.click();
    }

    public void clickGoBackLink(WebDriver driver) {
        waitForElement(driver, LINK_BACK_TO_LOGIN).click();
    }

    public void clickGoBackLink() {
        backLink.click();
    }

    public boolean isLoaded(WebDriver driver) {
        return isElementDisplayed(driver, BUTTON_SUBMIT);
    }
}

